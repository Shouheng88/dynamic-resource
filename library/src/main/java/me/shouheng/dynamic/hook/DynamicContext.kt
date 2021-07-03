package me.shouheng.dynamic.hook

import android.content.Context
import android.content.ContextWrapper
import android.content.res.AssetFileDescriptor
import android.content.res.ColorStateList
import android.content.res.Resources
import android.content.res.XmlResourceParser
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.util.TypedValue
import me.shouheng.dynamic.resources.IResources
import java.io.InputStream

/**
 * Context proxy.
 *
 * The weakness of this class is that, you cannot hook the method such as [getString],
 * since it's final. So we are not able to find out is given resource of string id found
 * in dynamic resources. So we are unable to make a downgrade when failed.
 */
class DynamicContext(base: Context) : ContextWrapper(base) {

    private var dynamicResources: DynamicResources? = null

    override fun getResources(): Resources {
        val resources = super.getResources()
        if (dynamicResources == null) {
            dynamicResources = DynamicResources(object : IResources {
                override fun getIdentifier(name: String, defType: String, defPackage: String): Int {
                    TODO("Not yet implemented")
                }

                override fun getString(id: Int): String {
                    TODO("Not yet implemented")
                }

                override fun getString(id: Int, vararg formatArgs: Any?): String {
                    TODO("Not yet implemented")
                }

                override fun getTextArray(id: Int): Array<CharSequence> {
                    TODO("Not yet implemented")
                }

                override fun getAnimation(id: Int): XmlResourceParser {
                    TODO("Not yet implemented")
                }

                override fun getText(id: Int): CharSequence {
                    TODO("Not yet implemented")
                }

                override fun getText(id: Int, def: CharSequence?): CharSequence {
                    TODO("Not yet implemented")
                }

                override fun getDrawableForDensity(id: Int, density: Int): Drawable? {
                    TODO("Not yet implemented")
                }

                override fun getDrawableForDensity(id: Int, density: Int, theme: Resources.Theme?): Drawable? {
                    TODO("Not yet implemented")
                }

                override fun getDimensionPixelSize(id: Int): Int {
                    TODO("Not yet implemented")
                }

                override fun getIntArray(id: Int): IntArray {
                    TODO("Not yet implemented")
                }

                override fun getQuantityString(id: Int, quantity: Int, vararg formatArgs: Any?): String {
                    TODO("Not yet implemented")
                }

                override fun getQuantityString(id: Int, quantity: Int): String {
                    TODO("Not yet implemented")
                }

                override fun getStringArray(id: Int): Array<String> {
                    TODO("Not yet implemented")
                }

                override fun openRawResourceFd(id: Int): AssetFileDescriptor {
                    TODO("Not yet implemented")
                }

                override fun getDimension(id: Int): Float {
                    TODO("Not yet implemented")
                }

                override fun getColorStateList(id: Int): ColorStateList {
                    TODO("Not yet implemented")
                }

                override fun getColorStateList(id: Int, theme: Resources.Theme?): ColorStateList {
                    TODO("Not yet implemented")
                }

                override fun getBoolean(id: Int): Boolean {
                    TODO("Not yet implemented")
                }

                override fun getQuantityText(id: Int, quantity: Int): CharSequence {
                    TODO("Not yet implemented")
                }

                override fun getColor(id: Int): Int {
                    TODO("Not yet implemented")
                }

                override fun getColor(id: Int, theme: Resources.Theme?): Int {
                    TODO("Not yet implemented")
                }

                override fun openRawResource(id: Int): InputStream {
                    TODO("Not yet implemented")
                }

                override fun openRawResource(id: Int, value: TypedValue?): InputStream {
                    TODO("Not yet implemented")
                }

                override fun getInteger(id: Int): Int {
                    TODO("Not yet implemented")
                }

                override fun getDrawable(id: Int): Drawable {
                    TODO("Not yet implemented")
                }

                override fun getDrawable(id: Int, theme: Resources.Theme?): Drawable {
                    TODO("Not yet implemented")
                }

                override fun getLayout(id: Int): XmlResourceParser {
                    TODO("Not yet implemented")
                }

                override fun getFont(id: Int): Typeface {
                    TODO("Not yet implemented")
                }

                override fun getXml(id: Int): XmlResourceParser {
                    TODO("Not yet implemented")
                }

            }, resources)
        }
        return dynamicResources!!
    }

}
