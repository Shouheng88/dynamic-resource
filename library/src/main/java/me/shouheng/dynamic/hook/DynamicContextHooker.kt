package me.shouheng.dynamic.hook

import android.app.Activity
import android.app.Application
import android.app.Service
import android.content.ContextWrapper
import android.view.ContextThemeWrapper

/** The dynamic context hooker. */
object DynamicContextHooker {

    /**
     * Hook application context.
     *
     * The mBase filed of [ContextWrapper] was set when [Application.attachBaseContext] was called.
     * So, you must initialize Dynamic after [Application.attachBaseContext].
     */
    fun hook(application: Application) {
        try {
            val baseContext = application.baseContext
            val field = ContextWrapper::class.java.getDeclaredField("mBase")
            field.isAccessible = true
            field.set(application, DynamicContext(baseContext))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /** Hook service. */
    fun hook(service: Service) {

    }

    /** Hook activity. */
    fun hook(activity: Activity) {
        try {
            val resources = DynamicResources(null, activity.resources)
            val filed = ContextThemeWrapper::class.java.getDeclaredField("mResources")
            filed.isAccessible = true
            filed.set(activity, resources)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}
