package me.shouheng.dynamicsample.activity

import android.os.Bundle
import android.support.design.widget.Snackbar
import me.shouheng.dynamic.annotation.DynamicResources
import me.shouheng.dynamicsample.R
import me.shouheng.dynamicsample.R.string.fab_click_message
import me.shouheng.dynamicsample.databinding.ActivityMainBinding
import me.shouheng.dynamicsample.fragment.FirstFragment
import me.shouheng.dynamicsample.fragment.SecondFragment
import me.shouheng.utils.ktx.onDebouncedClick
import me.shouheng.utils.ktx.stringOf
import me.shouheng.vmlib.comn.EmptyViewModel

/**
 * But default, the current activity can extend the enable state from its parent.
 * The [DynamicResources] of current activity has a higher priority.
 */
//@DynamicResources(enable = false)
class MainActivity : ParentActivity<EmptyViewModel, ActivityMainBinding>(), FragmentChangeInteraction {

    private val firstFragment by lazy { FirstFragment() }
    private val secondFragment by lazy { SecondFragment() }

    override fun doCreateView(savedInstanceState: Bundle?) {
        setSupportActionBar(binding.toolbar)
        binding.fab.onDebouncedClick {
            Snackbar.make(it, getString(fab_click_message), Snackbar.LENGTH_LONG)
                .setAction(stringOf(R.string.fab_click_action), null).show()
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
