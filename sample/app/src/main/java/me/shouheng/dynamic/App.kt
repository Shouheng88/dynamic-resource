package me.shouheng.dynamic

import android.app.Application
import me.shouheng.vmlib.VMLib

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        VMLib.onCreate(this)
    }
}
