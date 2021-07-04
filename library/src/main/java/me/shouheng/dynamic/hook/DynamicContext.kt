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
import me.shouheng.dynamic.Dynamic
import me.shouheng.dynamic.resources.DynamicResourcesChangeAware
import me.shouheng.dynamic.resources.IResources
import java.io.InputStream

/**
 * Context proxy.
 *
 * The weakness of this class is that, you cannot hook the method such as [getString],
 * since it's final. So we are not able to find out is given resource of string id found
 * in dynamic resources. So we are unable to make a downgrade when failed.
 */
class DynamicContext(
    base: Context,
    private val dynamic: Dynamic
) : ContextWrapper(base) {

    private var dynamicResources: DynamicResources? = null

    override fun getResources(): Resources {
        val resources = super.getResources()
        if (dynamicResources == null) {
            dynamicResources = DynamicResources(null, resources, dynamic)
        }
        return dynamicResources!!
    }

}
