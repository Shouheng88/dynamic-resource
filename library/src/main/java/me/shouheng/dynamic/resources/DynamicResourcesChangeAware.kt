package me.shouheng.dynamic.resources

import me.shouheng.dynamic.loader.SourceType

/** Dynamic resources changed event aware interface. */
interface DynamicResourcesChangeAware {

    /** Will be called when resources changed. */
    fun onResourcesChanged(resources: IResources, sourceType: SourceType)

}
