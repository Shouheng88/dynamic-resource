package me.shouheng.dynamicsample.fragment

import android.os.Bundle
import me.shouheng.dynamic.Dynamic
import me.shouheng.dynamic.loader.SourceType
import me.shouheng.dynamicsample.R
import me.shouheng.dynamicsample.databinding.FragmentMainBinding
import me.shouheng.utils.ktx.onDebouncedClick
import me.shouheng.vmlib.base.ViewBindingFragment
import me.shouheng.vmlib.comn.ContainerActivity
import me.shouheng.vmlib.comn.ContainerActivity.KEY_EXTRA_THEME_ID
import me.shouheng.vmlib.comn.EmptyViewModel

/** The first fragment. */
class MainFragment : ViewBindingFragment<EmptyViewModel, FragmentMainBinding>() {

    override fun doCreateView(savedInstanceState: Bundle?) {
        binding.buttonNext.onDebouncedClick {
            ContainerActivity.open(SecondFragment::class.java)
                .put(KEY_EXTRA_THEME_ID, R.style.AppTheme)
                .launch(context!!)
        }
        binding.btnLoadDefault.onDebouncedClick {
            Dynamic.get().load("", SourceType.DEFAULT, null)
        }
        binding.btnLoadNight.onDebouncedClick {
            Dynamic.get().load("night", SourceType.RESOURCES, null)
        }
        binding.btnLoadAssets.onDebouncedClick {
            Dynamic.get().load("assets.resc", SourceType.ASSETS, null)
        }
    }
}
