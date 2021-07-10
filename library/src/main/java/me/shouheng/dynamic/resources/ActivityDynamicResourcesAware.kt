package me.shouheng.dynamic.resources

import android.app.Activity
import me.shouheng.dynamic.loader.SourceType

/** Dynamic resources aware for activities. */
internal class ActivityDynamicResourcesAware : DynamicResourcesAware {

    private val dynamicResourcesAwareActivities = mutableListOf<DynamicResourcesAwareActivity>()

    override fun onResourcesChanged(resources: IResources, sourceType: SourceType) {
        dynamicResourcesAwareActivities.forEach {
            it.onResourcesChanged()
        }
    }

    fun addDynamicResourcesAwareActivity(activity: DynamicResourcesAwareActivity) {
        dynamicResourcesAwareActivities.add(activity)
    }

    fun removeDynamicResourcesAwareActivity(activity: DynamicResourcesAwareActivity) {
        dynamicResourcesAwareActivities.remove(activity)
    }
}

/**
 * Let your activity implement this interface to be dynamic resources aware activity.
 * If the dynamic resources is enabled for dynamic resources aware activity, it will
 * receive resources change event automatically.
 */
interface DynamicResourcesAwareActivity {

    /** Called when the resources changed. */
    fun onResourcesChanged()
}

/** Dynamic resources aware activity adapter. */
abstract class DynamicResourcesAwareActivityAdapter<T : Activity>(
    private val activity: T
): DynamicResourcesAwareActivity {

    override fun onResourcesChanged() {
        onResourcesChanged(activity)
    }

    /** Implement your own business when resources changed. */
    abstract fun onResourcesChanged(activity: T)
}

/**
 * The factory for dynamic resources aware activity adapter.
 * Used for activities out of our module.
 */
interface DynamicResourcesAwareActivityAdapterFactory<T : Activity> {

    /** Target activity class. */
    fun getTarget(): Class<T>

    /** Creator for adapter. */
    fun getAdapter(activity: Activity): DynamicResourcesAwareActivityAdapter<T>
}
