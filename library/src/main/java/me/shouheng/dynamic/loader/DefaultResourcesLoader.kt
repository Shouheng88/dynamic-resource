package me.shouheng.dynamic.loader

import android.content.Context
import me.shouheng.dynamic.resources.ResourcesImpl

/** Default resources loader. */
class DefaultResourcesLoader(
    private val context: Context
) : ResourcesLoader {

    override fun target(): SourceType = Source.DEFAULT

    override fun load(
        path: String,
        listener: ResourcesLoaderListener
    ) {
        listener.onStart()
        val appResources = context.resources
        listener.onSucceed(ResourcesImpl(appResources, context.packageName))
    }

}
