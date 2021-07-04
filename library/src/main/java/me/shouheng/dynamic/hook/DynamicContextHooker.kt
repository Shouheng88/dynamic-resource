package me.shouheng.dynamic.hook

import android.app.Activity
import android.app.Application
import android.app.Service
import android.content.ContextWrapper
import android.view.ContextThemeWrapper
import me.shouheng.dynamic.Dynamic

/** The dynamic context hooker. */
object DynamicContextHooker {

    /**
     * Hook application context.
     *
     * The mBase filed of [ContextWrapper] was set when [Application.attachBaseContext] was called.
     * So, you must initialize Dynamic after [Application.attachBaseContext].
     */
    fun hook(
        application: Application,
        dynamic: Dynamic
    ): DynamicContext? {
        try {
            val baseContext = application.baseContext
            val field = ContextWrapper::class.java.getDeclaredField("mBase")
            field.isAccessible = true
            val dynamicContext = DynamicContext(baseContext, dynamic)
            field.set(application, dynamicContext)
            return dynamicContext
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    /** Hook service. */
    fun hook(service: Service) {

    }

    /** Hook activity. */
    fun hook(
        activity: Activity,
        dynamic: Dynamic
    ): DynamicResources? {
        try {
            val dynamicResources = DynamicResources(null, activity.resources, dynamic)
            val filed = ContextThemeWrapper::class.java.getDeclaredField("mResources")
            filed.isAccessible = true
            filed.set(activity, dynamicResources)
            return dynamicResources
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

}
