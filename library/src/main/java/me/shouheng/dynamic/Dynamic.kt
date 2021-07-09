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
import me.shouheng.dynamic.store.DefaultSourceTypeStore
import me.shouheng.dynamic.store.ISourceTypeStore
import me.shouheng.dynamic.utils.DynamicL
import java.lang.ref.ReferenceQueue
import java.util.concurrent.*
import java.util.concurrent.atomic.AtomicInteger
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
    private var currentSourceType: SourceType = SourceType.DEFAULT
    private var currentSourcePath: String = ""
    private var poolWorkQueue = LinkedBlockingQueue<Runnable>(128)
    private val threadFactory: ThreadFactory = object : ThreadFactory {

        private val count = AtomicInteger(1)

        override fun newThread(r: Runnable): Thread {
            return Thread(r, "Dynamic #" + count.getAndIncrement())
        }
    }
    private var sourceTypeStore: ISourceTypeStore? = null

    private val readWriteLock = ReentrantReadWriteLock()

    private val loaders = mutableMapOf<SourceType, ResourcesLoader>()

    private val dynamicResourcesChangeAwareList = mutableListOf<WeakDynamicResourcesAware>()
    private val dynamicResourcesChangeAwareReferenceQueue = ReferenceQueue<DynamicResourcesAware>()

    internal var executor: Executor
    internal var currentResources: IResources? = null

    init {
        this.executor = ThreadPoolExecutor(
            CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_SECONDS, TimeUnit.SECONDS,
            poolWorkQueue, threadFactory)
    }

    /** If enable the dynamic manager globally. */
    var enabled: Boolean = true
        private set

    internal fun init(
        application: Application,
        enabled: Boolean,
        executor: Executor? = null,
        sourceTypeStore: ISourceTypeStore?
    ) {
        this.application = application
        this.enabled = enabled
        this.executor = executor ?: this.executor
        this.sourceTypeStore = sourceTypeStore?:DefaultSourceTypeStore(application)
        DynamicL.CONFIG = DynamicL.Config(application)
        dynamicActivityLifecycleCallbacks = DynamicActivityLifecycleCallbacks(this)
        application.registerActivityLifecycleCallbacks(dynamicActivityLifecycleCallbacks)
        if (enabled) {
            hookApplicationContext(application)
        }
        listOf(
            ExternalResourcesLoader(application, this),
            AssetsResourcesLoader(application, this),
            DefaultResourcesLoader(application),
            ResourcesResourcesLoader(application)
        ).forEach {
            loaders[it.target()] = it
        }
        sourceTypeStore?.let {
            load(it.getSourcePath(), it.getSourceType(), null)
        }
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
        sourceTypeStore?.saveSourceType(currentSourceType)
        sourceTypeStore?.saveSourcePath(currentSourcePath)
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
                dynamicResourcesChangeAwareReferenceQueue
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
        var removed = dynamicResourcesChangeAwareReferenceQueue.poll()
        while (removed != null && removed.get() == null) {
            dynamicResourcesChangeAwareList.remove(removed)
            removed = dynamicResourcesChangeAwareReferenceQueue.poll()
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

        /** The executor to load, move resources package and run background tasks. */
        var executor: Executor? = null

        /** Is log allowed. */
        var allowLog: Boolean = false

        /** The store for source type. */
        var sourceTypeStore: ISourceTypeStore? = null

        /**
         * Add one resources loader.
         *
         * @param loader the resources loader.
         * @param replace will replace the original resources loader of target type, true to replace,
         * else this loader will be ignored.
         */
        fun addLoader(loader: ResourcesLoader, replace: Boolean) {
            loaders.add(Pair(loader, replace))
        }

        fun build(): Dynamic {
            return dynamic.apply {
                checkNotNull(this@Builder.application) { "The field application is required!" }
                init(
                    this@Builder.application!!,
                    this@Builder.enabled,
                    this@Builder.executor,
                    this@Builder.sourceTypeStore
                )
                this@Builder.loaders.forEach {
                    addResourceLoader(it.first, it.second)
                }
                DynamicL.getConfig().setLogSwitch(allowLog)
            }
        }
    }

    companion object {
        private val CPU_COUNT = Runtime.getRuntime().availableProcessors()
        private val CORE_POOL_SIZE = 2.coerceAtLeast((CPU_COUNT - 1).coerceAtMost(4))
        private val MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1
        private const val KEEP_ALIVE_SECONDS = 30L

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
