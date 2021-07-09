package me.shouheng.dynamic.loader

import android.content.Context
import android.content.pm.PackageManager
import android.content.res.AssetManager
import android.content.res.Resources
import me.shouheng.dynamic.Dynamic
import me.shouheng.dynamic.resources.ExternalResources
import java.io.File

/** External resources loader. */
open class ExternalResourcesLoader(
    private val context: Context,
    private val dynamic: Dynamic
) : ResourcesLoader {

    override fun target(): SourceType = SourceType.EXTERNAL

    override fun load(
        path: String,
        listener: ResourcesLoaderListener
    ) {
        listener.onStart()

        val file = File(path)
        if (!file.exists()) {
            listener.onFailed(ERROR_CODE_FILE_NOT_FOUND, "resources file not found")
            return
        }

        dynamic.executor.execute {
            try {
                val manager = context.packageManager
                val info = manager.getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES)

                val assetManager = AssetManager::class.java.newInstance()
                val addAssetPath = assetManager.javaClass.getMethod("addAssetPath", String::class.java)
                addAssetPath.invoke(assetManager, path)

                val appResources = context.resources
                val resource = Resources(assetManager, appResources.displayMetrics, appResources.configuration)
                listener.onSucceed(ExternalResources(resource, info.packageName))
            } catch (e: Exception) {
                e.printStackTrace()
                listener.onFailed(ERROR_CODE_HOOK_ASSET_MANAGER, "failed due ${e.message}")
            }
        }
    }
}

