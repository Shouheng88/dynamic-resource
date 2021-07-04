package me.shouheng.dynamic.service

import android.app.Service
import android.content.Context
import me.shouheng.dynamic.Dynamic
import me.shouheng.dynamic.hook.DynamicContextHooker

/** Dynamic resources service. */
abstract class DynamicResourcesService : Service() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        // Hook the service, must called after context attached.
        DynamicContextHooker.hook(
            this,
            Dynamic.get()
        )
    }
}
