package me.shouheng.dynamic.hook

import android.content.Context
import android.content.ContextWrapper
import android.content.res.Resources
import me.shouheng.dynamic.Dynamic

/**
 * Context proxy.
 *
 * The weakness of this class is that, you cannot hook the method such as [getString],
 * since it's final. So we are not able to find out is given resource of string id found
 * in dynamic resources. So we are unable to make a downgrade when failed.
 */
class DynamicContext(
    private val target: String,
    base: Context,
    private val dynamic: Dynamic
) : ContextWrapper(base) {

    private var dynamicResources: DynamicResources? = null

    override fun getResources(): Resources {
        val resources = super.getResources()
        if (dynamicResources == null) {
            dynamicResources = DynamicResources(
                target, dynamic.currentResources, resources, dynamic
            )
        }
        return dynamicResources!!
    }

    override fun toString(): String {
        return "[$target]" + super.toString()
    }
}
