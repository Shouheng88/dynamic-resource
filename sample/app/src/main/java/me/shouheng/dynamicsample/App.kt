package me.shouheng.dynamicsample

import android.app.Application
import android.view.InflateException
import me.shouheng.dynamic.Dynamic
import me.shouheng.dynamic.dynamic
import me.shouheng.dynamicsample.dynamic.DynamicResourcesAwareActivityAdapterFactoryForContainerActivity
import me.shouheng.uix.widget.button.NormalButton
import me.shouheng.utils.ktx.colorOf
import me.shouheng.vmlib.VMLib

/** The Application for sample. */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        VMLib.onCreate(this)
        app = this
        dynamic = dynamic {
            // TODO copy the comment here!
            application = this@App
            enabled = true
            allowLog = true
            registerDynamicResourcesAwareActivityAdapterFactory(
                DynamicResourcesAwareActivityAdapterFactoryForContainerActivity()
            )
        }
        registerExceptionHandler()
        customUIX()
    }

    private fun registerExceptionHandler() {
        val handler = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler { t, e ->
            handler.uncaughtException(t, e)
            (e as? InflateException)?.let {
                it.cause?.printStackTrace()
            }
        }
    }

    private fun customUIX() {
        NormalButton.GlobalConfig.normalColor = colorOf(R.color.cold_theme_accent)
    }

    companion object {
        lateinit var app: App
        lateinit var dynamic: Dynamic
    }
}
