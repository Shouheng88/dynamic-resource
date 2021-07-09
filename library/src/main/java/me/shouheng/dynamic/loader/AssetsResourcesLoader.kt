package me.shouheng.dynamic.loader

import android.content.Context
import android.os.AsyncTask
import android.text.TextUtils
import me.shouheng.dynamic.utils.DynamicFile
import me.shouheng.dynamic.utils.DynamicIO
import me.shouheng.dynamic.utils.DynamicL
import me.shouheng.dynamic.utils.DynamicPath
import java.io.File

/** Assets resources loader. */
class AssetsResourcesLoader(
    private val context: Context
) : ExternalResourcesLoader(context) {

    override fun target(): SourceType = SourceType.ASSETS

    override fun load(path: String, listener: ResourcesLoaderListener) {
        val dataPath = DynamicPath.getInternalAppDataPath(context)
        if (!TextUtils.isEmpty(dataPath)) {
            AsyncTask.execute {
                val external = copyToExternalStorage(path, dataPath)
                if (external != null) {
                    super.load(external, listener)
                } else {
                    DynamicL.i("Failed to copy from assets to external storage.")
                }
            }
        } else {
            DynamicL.i("External storage is not available.")
        }
    }

    /** Copy resources from assets to external storage. */
    private fun copyToExternalStorage(
        path: String,
        root: String
    ): String? {
        val name = DynamicFile.getNameFromPath(path)
        val external = File(root, name).absolutePath
        val ins = context.assets.open(path)
        DynamicIO.writeFileFromIS(external, ins)
        return external
    }

}
