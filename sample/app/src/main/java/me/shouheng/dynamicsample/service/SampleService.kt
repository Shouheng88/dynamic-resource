package me.shouheng.dynamicsample.service

import android.content.Intent
import android.os.IBinder
import me.shouheng.dynamic.service.DynamicResourcesService
import me.shouheng.dynamicsample.R
import me.shouheng.utils.ktx.toast

/** The sample service. */
class SampleService : DynamicResourcesService() {

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        toast(getString(R.string.second_hello_from_service))
        stopSelf()
    }
}
