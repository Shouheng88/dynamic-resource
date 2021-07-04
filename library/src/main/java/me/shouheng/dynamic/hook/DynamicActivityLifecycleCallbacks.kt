package me.shouheng.dynamic.hook

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import me.shouheng.dynamic.Dynamic
import java.lang.ref.ReferenceQueue
import java.lang.ref.WeakReference

/** The dynamic activity life cycle callbacks. */
class DynamicActivityLifecycleCallbacks(
    private val dynamic: Dynamic
) : Application.ActivityLifecycleCallbacks {

    private val dynamicResourcesList = mutableListOf<KeyWeakDynamicResources>()
    private val referenceQueue = ReferenceQueue<DynamicResources>()

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
        if (isDynamicEnabled()
            && activity != null
            && isDynamicEnabledForActivity(activity)
        ) {
            hookActivityContext(activity)
        }
    }

    override fun onActivityStarted(activity: Activity?) { /*noop*/ }

    override fun onActivityResumed(activity: Activity?) { /*noop*/ }

    override fun onActivityPaused(activity: Activity?) { /*noop*/ }

    override fun onActivityStopped(activity: Activity?) { /*noop*/ }

    override fun onActivityDestroyed(activity: Activity?) {
        removeResourcesNoneExist()
    }

    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) { /*noop*/ }

    /** Hook the context of activity. */
    private fun hookActivityContext(activity: Activity) {
        DynamicContextHooker.hook(activity, dynamic)?.let {
            dynamicResourcesList.add(KeyWeakDynamicResources(it, referenceQueue))
        }
    }

    private fun removeResourcesNoneExist() {
        var removed = referenceQueue.poll()
        while (removed != null && removed.get() == null) {
            dynamicResourcesList.remove(removed)
            removed = referenceQueue.poll()
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

/** Key weak dynamic resources. */
class KeyWeakDynamicResources(
    referent: DynamicResources?,
    q: ReferenceQueue<in DynamicResources>?
) : WeakReference<DynamicResources>(referent, q)
