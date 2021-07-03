package me.shouheng.dynamic.hook

import android.app.Activity
import android.app.Application
import android.os.Bundle
import me.shouheng.dynamic.Dynamic

/** The dynamic activity life cycle callbacks. */
class DynamicActivityLifecycleCallbacks(
    private val dynamic: Dynamic
) : Application.ActivityLifecycleCallbacks {

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

    override fun onActivityDestroyed(activity: Activity?) { /*noop*/ }

    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) { /*noop*/ }

    /** Hook the context of activity. */
    private fun hookActivityContext(activity: Activity) {
        DynamicContextHooker.hook(activity)
    }

    /** Is dynamic resources enabled for given activity. */
    private fun isDynamicEnabledForActivity(activity: Activity): Boolean = true

    /** Is dynamic manager enabled. */
    private fun isDynamicEnabled(): Boolean = dynamic.enabled
}
