package me.shouheng.dynamic.activity

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import me.shouheng.dynamic.R
import me.shouheng.dynamic.databinding.ActivityMainBinding
import me.shouheng.utils.ktx.onDebouncedClick
import me.shouheng.vmlib.base.CommonActivity
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
