package me.shouheng.dynamic.loader

import android.content.Context
import android.content.res.*
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.AsyncTask
import android.os.Build
import android.support.annotation.RequiresApi
import android.util.TypedValue
import me.shouheng.dynamic.resources.IResources
import java.io.File
import java.io.InputStream

/** External resources loader. */
class ExternalResourcesLoader(
    private val context: Context
) : ResourcesLoader {

    override fun target(): SourceType = Source.EXTERNAL

    override fun load(path: String, listener: ResourcesLoaderListener) {
        listener.onStart()

        val file = File(path)
        if (!file.exists()) {
            listener.onFailed(ERROR_CODE_FILE_NOT_FOUND, "file not found")
            return
        }

        AsyncTask.execute {
            try {
                val assetManager = AssetManager::class.java.newInstance()
                val addAssetPath = assetManager.javaClass.getMethod("addAssetPath", String::class.java)
                addAssetPath.invoke(assetManager, path)

                val appResources = context.resources
                val resource = Resources(assetManager, appResources.displayMetrics, appResources.configuration)
                listener.onSucceed(ExternalResources(resource))
            } catch (e: Exception) {
                e.printStackTrace()
                listener.onFailed(ERROR_CODE_HOOK_ASSET_MANAGER, "failed due ${e.message}")
            }
        }
    }
}

/** External resources. */
class ExternalResources(
    private val resources: Resources
): IResources {

    override fun getIdentifier(name: String, defType: String, defPackage: String): Int {
        return resources.getIdentifier(name, defType, defPackage)
    }

    override fun getString(id: Int): String {
        return resources.getString(id)
    }

    override fun getString(id: Int, vararg formatArgs: Any?): String {
        return resources.getString(id)
    }

    override fun getTextArray(id: Int): Array<CharSequence> {
        return resources.getTextArray(id)
    }

    override fun getAnimation(id: Int): XmlResourceParser {
        return resources.getAnimation(id)
    }

    override fun getText(id: Int): CharSequence {
        return resources.getText(id)
    }

    override fun getText(id: Int, def: CharSequence?): CharSequence {
        return resources.getText(id)
    }

    override fun getDrawableForDensity(id: Int, density: Int): Drawable? {
        return resources.getDrawableForDensity(id, density)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun getDrawableForDensity(id: Int, density: Int, theme: Resources.Theme?): Drawable? {
        return resources.getDrawableForDensity(id, density, theme)
    }

    override fun getDimensionPixelSize(id: Int): Int {
        return resources.getDimensionPixelSize(id)
    }

    override fun getIntArray(id: Int): IntArray {
        return resources.getIntArray(id)
    }

    override fun getQuantityString(id: Int, quantity: Int, vararg formatArgs: Any?): String {
        return resources.getQuantityString(id, quantity, formatArgs)
    }

    override fun getQuantityString(id: Int, quantity: Int): String {
        return resources.getQuantityString(id, quantity)
    }

    override fun getStringArray(id: Int): Array<String> {
        return resources.getStringArray(id)
    }

    override fun openRawResourceFd(id: Int): AssetFileDescriptor {
        return resources.openRawResourceFd(id)
    }

    override fun getDimension(id: Int): Float {
        return resources.getDimension(id)
    }

    override fun getColorStateList(id: Int): ColorStateList {
        return resources.getColorStateList(id)
    }

    override fun getColorStateList(id: Int, theme: Resources.Theme?): ColorStateList {
        return resources.getColorStateList(id)
    }

    override fun getBoolean(id: Int): Boolean {
        return resources.getBoolean(id)
    }

    override fun getQuantityText(id: Int, quantity: Int): CharSequence {
        return resources.getQuantityText(id, quantity)
    }

    override fun getColor(id: Int): Int {
        return resources.getColor(id)
    }

    override fun getColor(id: Int, theme: Resources.Theme?): Int {
        return resources.getColor(id)
    }

    override fun openRawResource(id: Int): InputStream {
        return resources.openRawResource(id)
    }

    override fun openRawResource(id: Int, value: TypedValue?): InputStream {
        return resources.openRawResource(id)
    }

    override fun getInteger(id: Int): Int {
        return resources.getInteger(id)
    }

    override fun getDrawable(id: Int): Drawable {
        return resources.getDrawable(id)
    }

    override fun getDrawable(id: Int, theme: Resources.Theme?): Drawable {
        return resources.getDrawable(id)
    }

    override fun getLayout(id: Int): XmlResourceParser {
        return resources.getLayout(id)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getFont(id: Int): Typeface {
        return resources.getFont(id)
    }

    override fun getXml(id: Int): XmlResourceParser {
        return resources.getXml(id)
    }

}
