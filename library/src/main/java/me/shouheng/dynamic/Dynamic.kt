package me.shouheng.dynamic

import android.app.Application
import me.shouheng.dynamic.Dynamic.Companion.dynamic
import me.shouheng.dynamic.hook.DynamicActivityLifecycleCallbacks
import me.shouheng.dynamic.hook.DynamicContext
import me.shouheng.dynamic.hook.DynamicContextHooker
import me.shouheng.dynamic.loader.*
import me.shouheng.dynamic.resources.DynamicResourcesAware
import me.shouheng.dynamic.resources.IResources
import me.shouheng.dynamic.resources.WeakDynamicResourcesAware
import me.shouheng.utils.UtilsApp
import java.lang.ref.ReferenceQueue
import java.util.concurrent.locks.ReentrantReadWriteLock

@DslMarker
annotation class DynamicResourcesDSL

/**
 * The dynamic resources manager.
 *
 * Singleton. You are only able to get the instance of dynamic manager by
 * global method [dynamic]. Later you are able to use the instance in your App.
 */
class Dynamic private constructor() {

    private lateinit var application: Application
    private var dynamicActivityLifecycleCallbacks: DynamicActivityLifecycleCallbacks? = null
    private var appDynamicContext: DynamicContext? = null
    private var currentSourceType: SourceType = Source.DEFAULT
    private var currentSourcePath: String = ""
    internal var currentResources: IResources? = null
    private val readWriteLock = ReentrantReadWriteLock()

    private val loaders = mutableMapOf<SourceType, ResourcesLoader>()

    private val dynamicResourcesChangeAwareList = mutableListOf<WeakDynamicResourcesAware>()
    private val dynamicResourcesReferenceQueue = ReferenceQueue<DynamicResourcesAware>()

    private val defaultLoaders = mutableListOf<ResourcesLoader>()

    /** If enable the dynamic manager globally. */
    var enabled: Boolean = true
        private set

    internal fun init(
        application: Application,
        enabled: Boolean
    ) {
        this.application = application
        this.enabled = enabled
        UtilsApp.init(application)
        dynamicActivityLifecycleCallbacks = DynamicActivityLifecycleCallbacks(this)
        application.registerActivityLifecycleCallbacks(dynamicActivityLifecycleCallbacks)
        if (enabled) {
            hookApplicationContext(application)
        }
        defaultLoaders.add(ExternalResourcesLoader(application))
        defaultLoaders.add(AssetsResourcesLoader())
        defaultLoaders.add(DefaultResourcesLoader(application))
        defaultLoaders.add(ResourcesResourcesLoader(application))
        defaultLoaders.forEach { loaders[it.target()] = it }
    }

    /**
     * Load resources.
     *
     * @param path the path to load dynamic resources.
     * @param source the resources source.
     * @param listener the resource load state callback.
     */
    fun load(
        path: String,
        source: SourceType,
        listener: ResourcesLoaderListener?
    ) {
        if (!checkShouldReload(path, source)) return
        currentSourcePath = path
        currentSourceType = source
        loaders[source]?.load(
            path,
            object : ResourcesLoaderListener {
                override fun onStart() {
                    listener?.onStart()
                }

                override fun onSucceed(resources: IResources) {
                    currentResources = resources
                    notifyResourcesChanged(resources, source)
                    listener?.onSucceed(resources)
                }

                override fun onFailed(code: String, message: String) {
                    listener?.onFailed(code, message)
                }
            }
        )
    }

    /** Register dynamic resources change aware. */
    fun registerDynamicResourcesChangeAware(
        aware: DynamicResourcesAware
    ) {
        readWriteLock.writeLock().lock()
        val existed = dynamicResourcesChangeAwareList.find {
            it.get() == aware
        }
        if (existed == null) {
            dynamicResourcesChangeAwareList.add(WeakDynamicResourcesAware(
                aware.name()?:aware.javaClass.name,
                aware,
                dynamicResourcesReferenceQueue
            ))
        }
        readWriteLock.writeLock().unlock()
    }

    /** Unregister dynamic resources change aware. */
    fun unRegisterDynamicResourcesChangeAware(
        aware: DynamicResourcesAware
    ) {
        readWriteLock.writeLock().lock()
        dynamicResourcesChangeAwareList.find {
            it.get() == aware
        }?.let {
            dynamicResourcesChangeAwareList.remove(it)
        }
        readWriteLock.writeLock().unlock()
    }

    /** Clear none existed dynamic resources. */
    internal fun clearResourcesNoneExist() {
        readWriteLock.writeLock().lock()
        var removed = dynamicResourcesReferenceQueue.poll()
        while (removed != null && removed.get() == null) {
            dynamicResourcesChangeAwareList.remove(removed)
            removed = dynamicResourcesReferenceQueue.poll()
        }
        readWriteLock.writeLock().unlock()
    }

    /** Check should reload resources. */
    private fun checkShouldReload(path: String, source: SourceType): Boolean {
        return source != currentSourceType || path != currentSourcePath
    }

    /** Hook application context. */
    private fun hookApplicationContext(application: Application) {
        appDynamicContext = DynamicContextHooker.hook(application, this)
    }

    private fun notifyResourcesChanged(
        resources: IResources,
        source: SourceType
    ) {
        readWriteLock.readLock().lock()
        dynamicResourcesChangeAwareList.forEach {
            it.get()?.onResourcesChanged(resources, source)
        }
        readWriteLock.readLock().unlock()
    }

    private fun addResourceLoader(loader: ResourcesLoader, replace: Boolean) {
        val type = loader.target()
        if (loaders.contains(type) && !replace)
            return
        loaders[type] = loader
    }

    @DynamicResourcesDSL
    class Builder {
        /** Resources loaders. */
        private val loaders = mutableListOf<Pair<ResourcesLoader, Boolean>>()

        /** Required! The application to which the dynamic manager applied. */
        var application: Application? = null

        /** If enable the dynamic manager globally. */
        var enabled: Boolean = true

        /**
         * Add one resources loader.
         *
         * @param loader the resources loader.
         * @param replace will replace the original resources loader of target type, true to replace,
         * else this loader will be ignored.
         */
        fun addResourceLoader(loader: ResourcesLoader, replace: Boolean) {
            loaders.add(Pair(loader, replace))
        }

        fun build(): Dynamic {
            return dynamic.apply {
                checkNotNull(this@Builder.application) { "The field application is required!" }
                init(
                    this@Builder.application!!,
                    this@Builder.enabled
                )
                this@Builder.loaders.forEach {
                    addResourceLoader(it.first, it.second)
                }
            }
        }
    }

    companion object {
        /** The singleton dynamic instance. */
        private val dynamic = Dynamic()

        /** Get the dynamic instance. */
        fun get() = dynamic
    }
}

/** DSL to create dynamic manager. */
inline fun dynamic(
    init: Dynamic.Builder.() -> Unit
): Dynamic {
    val builder = Dynamic.Builder()
    builder.init()
    return builder.build()
}
