package me.shouheng.dynamicsample.activity

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.Menu
import me.shouheng.dynamic.annotation.DynamicResources
import me.shouheng.dynamicsample.R
import me.shouheng.dynamicsample.R.string.send_message
import me.shouheng.dynamicsample.base.ParentActivity
import me.shouheng.dynamicsample.databinding.ActivityMainBinding
import me.shouheng.dynamicsample.fragment.MainFragment
import me.shouheng.uix.pages.rate.RatingManager
import me.shouheng.utils.ktx.onDebouncedClick
import me.shouheng.utils.ktx.stringOf
import me.shouheng.utils.ui.ViewUtils
import me.shouheng.vmlib.comn.EmptyViewModel

/**
 * But default, the current activity can extend the enable state from its parent.
 * The [DynamicResources] of current activity has a higher priority.
 */
//@DynamicResources(enable = false)
class MainActivity : ParentActivity<EmptyViewModel, ActivityMainBinding>() {

    private val firstFragment by lazy { MainFragment() }

    override fun doCreateView(savedInstanceState: Bundle?) {
        setSupportActionBar(binding.toolbar)
        binding.fab.onDebouncedClick {
            Snackbar.make(it, getString(send_message), Snackbar.LENGTH_LONG)
                .setAction(stringOf(R.string.send_action), null).show()
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, firstFragment)
            .commit()
        setMenu(R.menu.main) {
            when(it.itemId) {
                R.id.main_item_feedback -> {
                    showRatingDialog()
                }
            }
        }
    }

    private fun showRatingDialog() {
        RatingManager.rateApp(
            onFeedback = {
                RatingManager.showFeedbackDialog({

                }, supportFragmentManager)
            },
            onMarket = {
                RatingManager.showMarketDialog({

                }, supportFragmentManager)
            },
            fragmentManager = supportFragmentManager
        )
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        ViewUtils.setIconsVisible(menu, true)
        return super.onCreateOptionsMenu(menu)
    }
}
