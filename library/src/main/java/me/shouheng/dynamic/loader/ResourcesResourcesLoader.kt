package me.shouheng.dynamic.loader

import android.content.Context
import me.shouheng.dynamic.resources.ResourcesResources

/** Resources resources loader. */
class ResourcesResourcesLoader(
    private val context: Context
) : ResourcesLoader {

    override fun target(): SourceType = SourceType.RESOURCES

    override fun load(path: String, listener: ResourcesLoaderListener) {
        listener.onStart()
        val appResources = context.resources
        listener.onSucceed(ResourcesResources(path, appResources, context.packageName))
    }

}
