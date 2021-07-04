package me.shouheng.dynamic.hook

import android.content.res.AssetFileDescriptor
import android.content.res.ColorStateList
import android.content.res.Resources
import android.content.res.XmlResourceParser
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.annotation.RequiresApi
import android.util.TypedValue
import me.shouheng.dynamic.Dynamic
import me.shouheng.dynamic.loader.Source
import me.shouheng.dynamic.loader.SourceType
import me.shouheng.dynamic.resources.DynamicResourcesAware
import me.shouheng.dynamic.resources.IResources
import java.io.InputStream

/**
 * Dynamic resources.
 *
 * For document of android resources:
 * @see <a href="https://developer.android.com/guide/topics/resources/available-resources.html">android resouces</a>
 */
class DynamicResources(
    private val target: String,
    private var resources: IResources?,
    appResources: Resources,
    private val dynamic: Dynamic
) : Resources(
    appResources.assets,
    appResources.displayMetrics,
    appResources.configuration
), DynamicResourcesAware {

    init {
        dynamic.registerDynamicResourcesChangeAware(this)
    }

    override fun getString(id: Int): String {
        var result = super.getString(id)
        if (resources == null || !dynamic.enabled) return result
        try {
            val typeName = super.getResourceTypeName(id)
            val entryName = super.getResourceEntryName(id)
            val resId = resources!!.getIdentifier(entryName, typeName)
            if (resId == 0) return result
            result = resources!!.getString(resId)
        } catch (e: NotFoundException) {
            e.printStackTrace()
        }
        return result
    }

    override fun getString(id: Int, vararg formatArgs: Any?): String {
        var result = super.getString(id, formatArgs)
        if (resources == null || !dynamic.enabled) return result
        try {
            val typeName = super.getResourceTypeName(id)
            val entryName = super.getResourceEntryName(id)
            val resId = resources!!.getIdentifier(entryName, typeName)
            if (resId == 0) return result
            result = resources!!.getString(resId, formatArgs)
        } catch (e: NotFoundException) {
            e.printStackTrace()
        }
        return result
    }

    override fun getTextArray(id: Int): Array<CharSequence> {
        var result = super.getTextArray(id)
        if (resources == null || !dynamic.enabled) return result
        try {
            val typeName = super.getResourceTypeName(id)
            val entryName = super.getResourceEntryName(id)
            val resId = resources!!.getIdentifier(entryName, typeName)
            if (resId == 0) return result
            result = resources!!.getTextArray(resId)
        } catch (e: NotFoundException) {
            e.printStackTrace()
        }
        return result
    }

    override fun getAnimation(id: Int): XmlResourceParser {
        var result = super.getAnimation(id)
        if (resources == null || !dynamic.enabled) return result
        try {
            val typeName = super.getResourceTypeName(id)
            val entryName = super.getResourceEntryName(id)
            val resId = resources!!.getIdentifier(entryName, typeName)
            if (resId == 0) return result
            result = resources!!.getAnimation(resId)
        } catch (e: NotFoundException) {
            e.printStackTrace()
        }
        return result
    }

    override fun getText(id: Int): CharSequence {
        var result = super.getText(id)
        if (resources == null || !dynamic.enabled) return result
        try {
            val typeName = super.getResourceTypeName(id)
            val entryName = super.getResourceEntryName(id)
            val resId = resources!!.getIdentifier(entryName, typeName)
            if (resId == 0) return result
            result = resources!!.getText(resId)
        } catch (e: NotFoundException) {
            e.printStackTrace()
        }
        return result
    }

    override fun getText(id: Int, def: CharSequence?): CharSequence {
        var result = super.getText(id, def)
        if (resources == null || !dynamic.enabled) return result
        try {
            val typeName = super.getResourceTypeName(id)
            val entryName = super.getResourceEntryName(id)
            val resId = resources!!.getIdentifier(entryName, typeName)
            if (resId == 0) return result
            result = resources!!.getText(resId, def)
        } catch (e: NotFoundException) {
            e.printStackTrace()
        }
        return result
    }

    override fun getDrawableForDensity(id: Int, density: Int): Drawable? {
        var result = super.getDrawableForDensity(id, density)
        if (resources == null || !dynamic.enabled) return result
        try {
            val typeName = super.getResourceTypeName(id)
            val entryName = super.getResourceEntryName(id)
            val resId = resources!!.getIdentifier(entryName, typeName)
            if (resId == 0) return result
            result = resources!!.getDrawableForDensity(resId, density)
        } catch (e: NotFoundException) {
            e.printStackTrace()
        }
        return result
    }

//    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
//    override fun getDrawableForDensity(id: Int, density: Int, theme: Theme?): Drawable? {
//        var result = super.getDrawableForDensity(id, density, theme)
//        if (resources == null || !dynamic.enabled) return result
//        try {
//            val typeName = super.getResourceTypeName(id)
//            val entryName = super.getResourceEntryName(id)
//            val resId = resources!!.getIdentifier(entryName, typeName)
//            if (resId == 0) return result
//            result = resources!!.getDrawableForDensity(resId, density, theme)
//        } catch (e: NotFoundException) {
//            e.printStackTrace()
//        }
//        return result
//    }

    override fun getDimensionPixelSize(id: Int): Int {
        var result = super.getDimensionPixelSize(id)
        if (resources == null || !dynamic.enabled) return result
        try {
            val typeName = super.getResourceTypeName(id)
            val entryName = super.getResourceEntryName(id)
            val resId = resources!!.getIdentifier(entryName, typeName)
            if (resId == 0) return result
            result = resources!!.getDimensionPixelSize(resId)
        } catch (e: NotFoundException) {
            e.printStackTrace()
        }
        return result
    }

    override fun getIntArray(id: Int): IntArray {
        var result = super.getIntArray(id)
        if (resources == null || !dynamic.enabled) return result
        try {
            val typeName = super.getResourceTypeName(id)
            val entryName = super.getResourceEntryName(id)
            val resId = resources!!.getIdentifier(entryName, typeName)
            if (resId == 0) return result
            result = resources!!.getIntArray(resId)
        } catch (e: NotFoundException) {
            e.printStackTrace()
        }
        return result
    }

    override fun getQuantityString(id: Int, quantity: Int, vararg formatArgs: Any?): String {
        var result = super.getQuantityString(id, quantity, formatArgs)
        if (resources == null || !dynamic.enabled) return result
        try {
            val typeName = super.getResourceTypeName(id)
            val entryName = super.getResourceEntryName(id)
            val resId = resources!!.getIdentifier(entryName, typeName)
            if (resId == 0) return result
            result = resources!!.getQuantityString(resId, quantity, formatArgs)
        } catch (e: NotFoundException) {
            e.printStackTrace()
        }
        return result
    }

    override fun getQuantityString(id: Int, quantity: Int): String {
        var result = super.getQuantityString(id, quantity)
        if (resources == null || !dynamic.enabled) return result
        try {
            val typeName = super.getResourceTypeName(id)
            val entryName = super.getResourceEntryName(id)
            val resId = resources!!.getIdentifier(entryName, typeName)
            if (resId == 0) return result
            result = resources!!.getQuantityString(resId, quantity)
        } catch (e: NotFoundException) {
            e.printStackTrace()
        }
        return result
    }

    override fun getStringArray(id: Int): Array<String> {
        var result = super.getStringArray(id)
        if (resources == null || !dynamic.enabled) return result
        try {
            val typeName = super.getResourceTypeName(id)
            val entryName = super.getResourceEntryName(id)
            val resId = resources!!.getIdentifier(entryName, typeName)
            if (resId == 0) return result
            result = resources!!.getStringArray(resId)
        } catch (e: NotFoundException) {
            e.printStackTrace()
        }
        return result
    }

    override fun openRawResourceFd(id: Int): AssetFileDescriptor {
        var result = super.openRawResourceFd(id)
        if (resources == null || !dynamic.enabled) return result
        try {
            val typeName = super.getResourceTypeName(id)
            val entryName = super.getResourceEntryName(id)
            val resId = resources!!.getIdentifier(entryName, typeName)
            if (resId == 0) return result
            result = resources!!.openRawResourceFd(resId)
        } catch (e: NotFoundException) {
            e.printStackTrace()
        }
        return result
    }

    override fun getDimension(id: Int): Float {
        var result = super.getDimension(id)
        if (resources == null || !dynamic.enabled) return result
        try {
            val typeName = super.getResourceTypeName(id)
            val entryName = super.getResourceEntryName(id)
            val resId = resources!!.getIdentifier(entryName, typeName)
            if (resId == 0) return result
            result = resources!!.getDimension(resId)
        } catch (e: NotFoundException) {
            e.printStackTrace()
        }
        return result
    }

    override fun getColorStateList(id: Int): ColorStateList {
        var result = super.getColorStateList(id)
        if (resources == null || !dynamic.enabled) return result
        try {
            val typeName = super.getResourceTypeName(id)
            val entryName = super.getResourceEntryName(id)
            val resId = resources!!.getIdentifier(entryName, typeName)
            if (resId == 0) return result
            result = resources!!.getColorStateList(resId)
        } catch (e: NotFoundException) {
            e.printStackTrace()
        }
        return result
    }

//    override fun getColorStateList(id: Int, theme: Theme?): ColorStateList {
//        var result = super.getColorStateList(id, theme)
//        if (resources == null || !dynamic.enabled) return result
//        try {
//            val typeName = super.getResourceTypeName(id)
//            val entryName = super.getResourceEntryName(id)
//            val resId = resources!!.getIdentifier(entryName, typeName)
//            if (resId == 0) return result
//            result = resources!!.getColorStateList(resId, theme)
//        } catch (e: NotFoundException) {
//            e.printStackTrace()
//        }
//        return result
//    }

    override fun getBoolean(id: Int): Boolean {
        var result = super.getBoolean(id)
        if (resources == null || !dynamic.enabled) return result
        try {
            val typeName = super.getResourceTypeName(id)
            val entryName = super.getResourceEntryName(id)
            val resId = resources!!.getIdentifier(entryName, typeName)
            if (resId == 0) return result
            result = resources!!.getBoolean(resId)
        } catch (e: NotFoundException) {
            e.printStackTrace()
        }
        return result
    }

    override fun getQuantityText(id: Int, quantity: Int): CharSequence {
        var result = super.getQuantityText(id, quantity)
        if (resources == null || !dynamic.enabled) return result
        try {
            val typeName = super.getResourceTypeName(id)
            val entryName = super.getResourceEntryName(id)
            val resId = resources!!.getIdentifier(entryName, typeName)
            if (resId == 0) return result
            result = resources!!.getQuantityText(resId, quantity)
        } catch (e: NotFoundException) {
            e.printStackTrace()
        }
        return result
    }

    override fun getColor(id: Int): Int {
        var result = super.getColor(id)
        if (resources == null || !dynamic.enabled) return result
        try {
            val typeName = super.getResourceTypeName(id)
            val entryName = super.getResourceEntryName(id)
            val resId = resources!!.getIdentifier(entryName, typeName)
            if (resId == 0) return result
            result = resources!!.getColor(resId)
        } catch (e: NotFoundException) {
            e.printStackTrace()
        }
        return result
    }

    // TODO test the dynamic effect for method with theme, xml, layout etc.
//    override fun getColor(id: Int, theme: Theme?): Int {
//        var result = super.getColor(id, theme)
//        if (resources == null || !dynamic.enabled)
//            return result
//        try {
//            val typeName = super.getResourceTypeName(id)
//            val entryName = super.getResourceEntryName(id)
//            val resId = resources!!.getIdentifier(entryName, typeName)
//            if (resId == 0) return result
//            result = resources!!.getColor(resId, theme)
//        } catch (e: NotFoundException) {
//            e.printStackTrace()
//        }
//        return result
//    }

    override fun openRawResource(id: Int): InputStream {
        var result = super.openRawResource(id)
        if (resources == null || !dynamic.enabled)
            return result
        try {
            val typeName = super.getResourceTypeName(id)
            val entryName = super.getResourceEntryName(id)
            val resId = resources!!.getIdentifier(entryName, typeName)
            if (resId == 0) return result
            result = resources!!.openRawResource(resId)
        } catch (e: NotFoundException) {
            e.printStackTrace()
        }
        return result
    }

    override fun openRawResource(id: Int, value: TypedValue?): InputStream {
        var result = super.openRawResource(id, value)
        if (resources == null || !dynamic.enabled)
            return result
        try {
            val typeName = super.getResourceTypeName(id)
            val entryName = super.getResourceEntryName(id)
            val resId = resources!!.getIdentifier(entryName, typeName)
            if (resId == 0) return result
            result = resources!!.openRawResource(resId, value)
        } catch (e: NotFoundException) {
            e.printStackTrace()
        }
        return result
    }

    override fun getInteger(id: Int): Int {
        var result = super.getInteger(id)
        if (resources == null || !dynamic.enabled) return result
        try {
            val typeName = super.getResourceTypeName(id)
            val entryName = super.getResourceEntryName(id)
            val resId = resources!!.getIdentifier(entryName, typeName)
            if (resId == 0) return result
            result = resources!!.getInteger(resId)
        } catch (e: NotFoundException) {
            e.printStackTrace()
        }
        return result
    }

    override fun getDrawable(id: Int): Drawable {
        var result = super.getDrawable(id)
        if (resources == null || !dynamic.enabled) return result
        try {
            val typeName = super.getResourceTypeName(id)
            val entryName = super.getResourceEntryName(id)
            val resId = resources!!.getIdentifier(entryName, typeName)
            if (resId == 0) return result
            result = resources!!.getDrawable(resId)
        } catch (e: NotFoundException) {
            e.printStackTrace()
        }
        return result
    }

//    override fun getDrawable(id: Int, theme: Theme?): Drawable {
//        var result = super.getDrawable(id, theme)
//        if (resources == null || !dynamic.enabled) return result
//        try {
//            val typeName = super.getResourceTypeName(id)
//            val entryName = super.getResourceEntryName(id)
//            val resId = resources!!.getIdentifier(entryName, typeName)
//            if (resId == 0) return result
//            result = resources!!.getDrawable(resId, theme)
//        } catch (e: NotFoundException) {
//            e.printStackTrace()
//        }
//        return result
//    }

//    override fun getLayout(id: Int): XmlResourceParser {
//        var result = super.getLayout(id)
//        if (resources == null || !dynamic.enabled) return result
//        try {
//            val typeName = super.getResourceTypeName(id)
//            val entryName = super.getResourceEntryName(id)
//            val resId = resources!!.getIdentifier(entryName, typeName)
//            if (resId == 0) return result
//            result = resources!!.getLayout(resId)
//        } catch (e: NotFoundException) {
//            e.printStackTrace()
//        }
//        return result
//    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getFont(id: Int): Typeface {
        var result = super.getFont(id)
        if (resources == null || !dynamic.enabled) return result
        try {
            val typeName = super.getResourceTypeName(id)
            val entryName = super.getResourceEntryName(id)
            val resId = resources!!.getIdentifier(entryName, typeName)
            if (resId == 0) return result
            result = resources!!.getFont(resId)
        } catch (e: NotFoundException) {
            e.printStackTrace()
        }
        return result
    }

    override fun getXml(id: Int): XmlResourceParser {
        var result = super.getXml(id)
        if (resources == null || !dynamic.enabled) return result
        try {
            val typeName = super.getResourceTypeName(id)
            val entryName = super.getResourceEntryName(id)
            val resId = resources!!.getIdentifier(entryName, typeName)
            if (resId == 0) return result
            result = resources!!.getXml(resId)
        } catch (e: NotFoundException) {
            e.printStackTrace()
        }
        return result
    }

    override fun onResourcesChanged(resources: IResources, sourceType: SourceType) {
        if (sourceType != Source.DEFAULT) {
            this.resources = resources
        } else {
            this.resources = null
        }
    }

    override fun toString(): String {
        return "[$target]" + super.toString()
    }

    override fun name(): String? = target
}
