package me.shouheng.dynamic.resources

import me.shouheng.dynamic.loader.SourceType

/** Dynamic resources aware for fragments. */
internal class FragmentDynamicResourcesAware : DynamicResourcesAware {

    private val dynamicResourcesAwareFragments = mutableListOf<DynamicResourcesAwareFragment>()

    override fun onResourcesChanged(resources: IResources, sourceType: SourceType) {
        dynamicResourcesAwareFragments.forEach {
            it.onResourcesChanged()
        }
    }

    fun addDynamicResourcesAwareFragment(fragment: DynamicResourcesAwareFragment) {
        dynamicResourcesAwareFragments.add(fragment)
    }

    fun removeDynamicResourcesAwareFragment(fragment: DynamicResourcesAwareFragment) {
        dynamicResourcesAwareFragments.remove(fragment)
    }
}

/**
 * Let your fragment implement this interface to be dynamic resources aware fragment.
 * If the dynamic resources is enabled for dynamic resources aware fragment, it will
 * receive resources change event automatically. Since the resources of fragment of
 * activity is derived from activity, so the enable state of fragment is same as its
 * activity.
 */
interface DynamicResourcesAwareFragment {

    /** Called when the resources changed. */
    fun onResourcesChanged()
}
