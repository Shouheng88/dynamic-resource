package me.shouheng.dynamicsample.dynamic

import android.app.Activity
import me.shouheng.dynamic.resources.DynamicResourcesAwareActivityAdapter
import me.shouheng.dynamic.resources.DynamicResourcesAwareActivityAdapterFactory
import me.shouheng.vmlib.comn.ContainerActivity

/** The [DynamicResourcesAwareActivityAdapterFactory] for [ContainerActivity].  */
class DynamicResourcesAwareActivityAdapterFactoryForContainerActivity :
    DynamicResourcesAwareActivityAdapterFactory<ContainerActivity> {

    override fun getTarget(): Class<ContainerActivity> = ContainerActivity::class.java

    override fun getAdapter(activity: Activity): DynamicResourcesAwareActivityAdapter<ContainerActivity> {
        return DynamicResourcesAwareActivityAdapterForContainerActivity(activity as ContainerActivity)
    }
}

/** The [DynamicResourcesAwareActivityAdapter] for [ContainerActivity]. */
class DynamicResourcesAwareActivityAdapterForContainerActivity(
    activity: ContainerActivity
) : DynamicResourcesAwareActivityAdapter<ContainerActivity>(activity) {

    override fun onResourcesChanged(activity: ContainerActivity) {

    }
}
