package me.shouheng.dynamicsample.activity

import android.os.Bundle
import android.support.design.widget.Snackbar
import me.shouheng.dynamicsample.R
import me.shouheng.dynamicsample.databinding.ActivityMainBinding
import me.shouheng.dynamicsample.fragment.FirstFragment
import me.shouheng.dynamicsample.fragment.SecondFragment
import me.shouheng.utils.ktx.onDebouncedClick
import me.shouheng.vmlib.base.ViewBindingActivity
import me.shouheng.vmlib.comn.EmptyViewModel

class MainActivity : ViewBindingActivity<EmptyViewModel, ActivityMainBinding>(), FragmentChangeInteraction {

    private val firstFragment by lazy { FirstFragment() }
    private val secondFragment by lazy { SecondFragment() }

    override fun doCreateView(savedInstanceState: Bundle?) {
        setSupportActionBar(binding.toolbar)
        binding.fab.onDebouncedClick {
            Snackbar.make(it, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        changeToFirstFragment()
    }

    override fun changeToFirstFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, firstFragment)
            .commit()
    }

    override fun changeToSecondFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, secondFragment)
            .commit()
    }
}

interface FragmentChangeInteraction {

    fun changeToFirstFragment()

    fun changeToSecondFragment()
}
