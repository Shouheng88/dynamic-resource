package me.shouheng.dynamic.hook

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AppCompatActivity
import me.shouheng.dynamic.Dynamic
import me.shouheng.dynamic.resources.*
import me.shouheng.dynamic.resources.ActivityDynamicResourcesAware
import me.shouheng.dynamic.resources.FragmentDynamicResourcesAware

/** The dynamic activity life cycle callbacks. */
internal class DynamicActivityLifecycleCallbacks(
    private val dynamic: Dynamic
) : Application.ActivityLifecycleCallbacks {

    private val activityDynamicResourcesAware = ActivityDynamicResourcesAware()
    private val fragmentDynamicResourcesAware = FragmentDynamicResourcesAware()
    private val activityAdapterFactoryMap =
        mutableMapOf<Class<out Activity>, DynamicResourcesAwareActivityAdapterFactory<out Activity>>()
    private val activityAdapterMap = mutableMapOf<Activity, DynamicResourcesAwareActivityAdapter<out Activity>>()

    init {
        dynamic.registerDynamicResourcesChangeAware(activityDynamicResourcesAware)
        dynamic.registerDynamicResourcesChangeAware(fragmentDynamicResourcesAware)
        dynamic.dynamicResourcesAwareActivityAdapterFactories.forEach {
            activityAdapterFactoryMap[it.getTarget()] = it
        }
    }

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
        if (isDynamicEnabled()
            && activity != null
            // The activity will be registered if its dynamic resources enabled or it's instance
            // of DynamicResourcesAwareActivity.
            && (isDynamicEnabledForActivity(activity)
                    || activity is DynamicResourcesAwareActivity
                    || activityAdapterFactoryMap.containsKey(activity.javaClass))
        ) {
            hookActivityContext(activity)
        }
    }

    override fun onActivityStarted(activity: Activity?) { /*noop*/ }

    override fun onActivityResumed(activity: Activity?) { /*noop*/ }

    override fun onActivityPaused(activity: Activity?) { /*noop*/ }

    override fun onActivityStopped(activity: Activity?) { /*noop*/ }

    override fun onActivityDestroyed(activity: Activity?) {
        dynamic.clearResourcesNoneExist()
        if (activity is DynamicResourcesAwareActivity) {
            activityDynamicResourcesAware.removeDynamicResourcesAwareActivity(activity)
        }
        activityAdapterMap.remove(activity)?.let {
            activityDynamicResourcesAware.removeDynamicResourcesAwareActivity(it)
        }
    }

    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) { /*noop*/ }

    /** Hook the context of activity. */
    private fun hookActivityContext(activity: Activity) {
        DynamicContextHooker.hook(activity, dynamic)
        if (activity is DynamicResourcesAwareActivity) {
            activityDynamicResourcesAware.addDynamicResourcesAwareActivity(activity)
        }
        activityAdapterFactoryMap[activity.javaClass]?.let {
            val adapter = it.getAdapter(activity)
            activityDynamicResourcesAware.addDynamicResourcesAwareActivity(adapter)
            activityAdapterMap[activity] = adapter
        }
        if (activity is FragmentActivity) {
            activity.supportFragmentManager.registerFragmentLifecycleCallbacks(
                DynamicFragmentLifecycleCallbacks(fragmentDynamicResourcesAware), true
            )
        }
    }

    /** Is dynamic resources enabled for given activity. */
    private fun isDynamicEnabledForActivity(activity: Activity): Boolean {
        // The activity itself is prior.
        if (activity.javaClass.isAnnotationPresent(
                me.shouheng.dynamic.annotation.DynamicResources::class.java)
        ) {
            val dynamicResources = activity.javaClass.getAnnotation(
                me.shouheng.dynamic.annotation.DynamicResources::class.java
            )
            if (dynamicResources != null)
                return dynamicResources.enable
        }
        // Then extend from its parent.
        var superActivity = activity::class.java.superclass
        while (superActivity != null
            && superActivity != Activity::class.java
            && superActivity != AppCompatActivity::class.java
        ) {
            if (superActivity.isAnnotationPresent(
                    me.shouheng.dynamic.annotation.DynamicResources::class.java)
            ) {
                val dynamicResources = superActivity.getAnnotation(
                    me.shouheng.dynamic.annotation.DynamicResources::class.java
                )
                if (dynamicResources != null)
                    return dynamicResources.enable
            }
            superActivity = superActivity.superclass
        }
        return false
    }

    /** Is dynamic manager enabled. */
    private fun isDynamicEnabled(): Boolean = dynamic.enabled
}
