package me.shouheng.dynamicsample

import android.app.Application
import me.shouheng.dynamic.Dynamic
import me.shouheng.dynamic.dynamic
import me.shouheng.dynamic.loader.ResourcesLoader
import me.shouheng.dynamic.loader.ResourcesLoaderListener
import me.shouheng.dynamic.loader.SourceType
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
            addResourceLoader(object : ResourcesLoader {
                override fun target(): SourceType = object : SourceType {}

                override fun load(path: String, listener: ResourcesLoaderListener) {
                    // noop
                }
            }, true)
        }
    }

    companion object {
        lateinit var app: App
        lateinit var dynamic: Dynamic
    }
}
