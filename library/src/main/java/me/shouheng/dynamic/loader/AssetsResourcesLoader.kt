package me.shouheng.dynamic.loader

/** Assets resources loader. */
class AssetsResourcesLoader : ResourcesLoader {

    override fun target(): SourceType = SourceType.ASSETS

    override fun load(path: String, listener: ResourcesLoaderListener) {
    }

}
