package me.shouheng.dynamic.hook

import android.content.res.*
import android.graphics.Movie
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.util.TypedValue
import me.shouheng.dynamic.Dynamic
import me.shouheng.dynamic.loader.Source
import me.shouheng.dynamic.loader.SourceType
import me.shouheng.dynamic.resources.DynamicResourcesChangeAware
import me.shouheng.dynamic.resources.IResources
import java.io.InputStream

/** Dynamic resources */
class DynamicResources(
    private var resources: IResources?,
    appResources: Resources,
    dynamic: Dynamic
) : Resources(
    appResources.assets,
    appResources.displayMetrics,
    appResources.configuration
), DynamicResourcesChangeAware {

    init {
        dynamic.registerDynamicResourcesChangeAware(this)
    }

    override fun getString(id: Int): String {
        var result = super.getString(id)
        if (resources == null) return result
        try {
            val entryName = getResourceEntryName(id)
            val resId = resources!!.getIdentifier(entryName, "string")
            result = resources!!.getString(resId)
        } catch (e: NotFoundException) {
            e.printStackTrace()
        }
        return result
    }

    override fun getString(id: Int, vararg formatArgs: Any?): String {
        var result = super.getString(id, formatArgs)
        if (resources == null) return result
        try {
            val entryName = getResourceEntryName(id)
            val resId = resources!!.getIdentifier(entryName, "string")
            result = resources!!.getString(resId, formatArgs)
        } catch (e: NotFoundException) {
            e.printStackTrace()
        }
        return result
    }

    override fun getTextArray(id: Int): Array<CharSequence> {
        return super.getTextArray(id)
    }

    override fun getAnimation(id: Int): XmlResourceParser {
        return super.getAnimation(id)
    }

    override fun getText(id: Int): CharSequence {
        return super.getText(id)
    }

    override fun getText(id: Int, def: CharSequence?): CharSequence {
        return super.getText(id, def)
    }

    override fun getDrawableForDensity(id: Int, density: Int): Drawable? {
        return super.getDrawableForDensity(id, density)
    }

    override fun getDrawableForDensity(id: Int, density: Int, theme: Theme?): Drawable? {
        return super.getDrawableForDensity(id, density, theme)
    }

    override fun getDimensionPixelSize(id: Int): Int {
        return super.getDimensionPixelSize(id)
    }

    override fun getIntArray(id: Int): IntArray {
        return super.getIntArray(id)
    }

    override fun getValue(id: Int, outValue: TypedValue?, resolveRefs: Boolean) {
        super.getValue(id, outValue, resolveRefs)
    }

    override fun getQuantityString(id: Int, quantity: Int, vararg formatArgs: Any?): String {
        return super.getQuantityString(id, quantity, *formatArgs)
    }

    override fun getQuantityString(id: Int, quantity: Int): String {
        return super.getQuantityString(id, quantity)
    }

    override fun getStringArray(id: Int): Array<String> {
        return super.getStringArray(id)
    }

    override fun openRawResourceFd(id: Int): AssetFileDescriptor {
        return super.openRawResourceFd(id)
    }

    override fun getDimension(id: Int): Float {
        return super.getDimension(id)
    }

    override fun getColorStateList(id: Int): ColorStateList {
        return super.getColorStateList(id)
    }

    override fun getColorStateList(id: Int, theme: Theme?): ColorStateList {
        return super.getColorStateList(id, theme)
    }

    override fun getBoolean(id: Int): Boolean {
        return super.getBoolean(id)
    }

    override fun getQuantityText(id: Int, quantity: Int): CharSequence {
        return super.getQuantityText(id, quantity)
    }

    override fun getColor(id: Int): Int {
        return super.getColor(id)
    }

    override fun getColor(id: Int, theme: Theme?): Int {
        return super.getColor(id, theme)
    }

    override fun openRawResource(id: Int): InputStream {
        return super.openRawResource(id)
    }

    override fun openRawResource(id: Int, value: TypedValue?): InputStream {
        return super.openRawResource(id, value)
    }

    override fun getMovie(id: Int): Movie {
        return super.getMovie(id)
    }

    override fun getInteger(id: Int): Int {
        return super.getInteger(id)
    }

    override fun getDrawable(id: Int): Drawable {
        return super.getDrawable(id)
    }

    override fun getDrawable(id: Int, theme: Theme?): Drawable {
        return super.getDrawable(id, theme)
    }

    override fun getResourceTypeName(resid: Int): String {
        return super.getResourceTypeName(resid)
    }

    override fun getLayout(id: Int): XmlResourceParser {
        return super.getLayout(id)
    }

    override fun getFont(id: Int): Typeface {
        return super.getFont(id)
    }

    override fun getXml(id: Int): XmlResourceParser {
        return super.getXml(id)
    }

    override fun onResourcesChanged(resources: IResources, sourceType: SourceType) {
        if (sourceType != Source.DEFAULT) {
            this.resources = resources
        } else {
            this.resources = null
        }
    }
}
