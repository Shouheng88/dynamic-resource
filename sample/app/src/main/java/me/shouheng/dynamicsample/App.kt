package me.shouheng.dynamicsample

import android.Manifest
import android.annotation.SuppressLint
import android.app.Application
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.view.InflateException
import me.shouheng.dynamic.Dynamic
import me.shouheng.dynamic.dynamic
import me.shouheng.dynamic.loader.ResourcesLoader
import me.shouheng.dynamic.loader.ResourcesLoaderListener
import me.shouheng.dynamic.loader.SourceType
import me.shouheng.utils.stability.CrashHelper
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
            addResourceLoader(object : ResourcesLoader {
                override fun target(): SourceType = object : SourceType {
                    override fun typeName(): String = "custom"
                }

                override fun load(path: String, listener: ResourcesLoaderListener) {
                    // noop
                }
            }, true)
        }
        registerExceptionHandler()
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

    companion object {
        lateinit var app: App
        lateinit var dynamic: Dynamic
    }
}
