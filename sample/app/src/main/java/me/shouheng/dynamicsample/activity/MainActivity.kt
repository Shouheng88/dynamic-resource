package me.shouheng.dynamicsample.activity

import android.os.Bundle
import android.support.design.widget.Snackbar
import me.shouheng.dynamicsample.databinding.ActivityMainBinding
import me.shouheng.utils.ktx.onDebouncedClick
import me.shouheng.vmlib.base.ViewBindingActivity
import me.shouheng.vmlib.comn.EmptyViewModel

class MainActivity : ViewBindingActivity<EmptyViewModel, ActivityMainBinding>() {

    override fun doCreateView(savedInstanceState: Bundle?) {
        setSupportActionBar(binding.toolbar)
        binding.fab.onDebouncedClick {
            Snackbar.make(it, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

}
