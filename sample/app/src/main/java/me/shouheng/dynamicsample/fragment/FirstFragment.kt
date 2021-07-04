package me.shouheng.dynamicsample.fragment

import android.os.Bundle
import me.shouheng.dynamic.loader.Source
import me.shouheng.dynamicsample.App
import me.shouheng.dynamicsample.activity.FragmentChangeInteraction
import me.shouheng.dynamicsample.databinding.FragmentFirstBinding
import me.shouheng.utils.ktx.onDebouncedClick
import me.shouheng.vmlib.base.ViewBindingFragment
import me.shouheng.vmlib.comn.EmptyViewModel

class FirstFragment : ViewBindingFragment<EmptyViewModel, FragmentFirstBinding>() {

    override fun doCreateView(savedInstanceState: Bundle?) {
        binding.buttonFirst.onDebouncedClick {
            (activity as? FragmentChangeInteraction)?.changeToSecondFragment()
        }
        binding.btnLoadDefault.onDebouncedClick {
            App.dynamic.load("", Source.DEFAULT, null)
        }
        binding.btnLoadAnother.onDebouncedClick {
            App.dynamic.load("another", Source.RESOURCES, null)
        }
    }
}
