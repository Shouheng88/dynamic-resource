package me.shouheng.dynamic.resources

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
