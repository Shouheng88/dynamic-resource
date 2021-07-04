package me.shouheng.dynamic.resources

import android.content.res.Resources

/** App resources resources loader. */
class ResourcesResources(
    private val dirName: String,
    resources: Resources,
    packageName: String
) : ResourcesImpl(resources, packageName) {

    override fun getIdentifier(name: String, defType: String?): Int {
        return super.getIdentifier("${name}_$dirName", defType)
    }
}
