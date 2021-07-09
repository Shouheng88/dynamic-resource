package me.shouheng.dynamicsample.fragment

import android.os.Bundle
import me.shouheng.dynamic.Dynamic
import me.shouheng.dynamic.loader.Source
import me.shouheng.dynamicsample.activity.FragmentChangeInteraction
import me.shouheng.dynamicsample.databinding.FragmentFirstBinding
import me.shouheng.utils.ktx.onDebouncedClick
import me.shouheng.vmlib.base.ViewBindingFragment
import me.shouheng.vmlib.comn.EmptyViewModel

/** The first fragment. */
class FirstFragment : ViewBindingFragment<EmptyViewModel, FragmentFirstBinding>() {

    override fun doCreateView(savedInstanceState: Bundle?) {
        binding.buttonFirst.onDebouncedClick {
            (activity as? FragmentChangeInteraction)?.changeToSecondFragment()
        }
        binding.btnLoadDefault.onDebouncedClick {
            Dynamic.get().load("", Source.DEFAULT, null)
        }
        binding.btnLoadAnother.onDebouncedClick {
            Dynamic.get().load("another", Source.RESOURCES, null)
        }
        binding.btnLoadAssets.onDebouncedClick {
            Dynamic.get().load("assets.resc", Source.ASSETS, null)
        }
    }
}
