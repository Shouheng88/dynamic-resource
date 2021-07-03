package me.shouheng.dynamicsample.fragment

import android.os.Bundle
import me.shouheng.dynamicsample.activity.FragmentChangeInteraction
import me.shouheng.dynamicsample.databinding.FragmentSecondBinding
import me.shouheng.utils.ktx.onDebouncedClick
import me.shouheng.vmlib.base.ViewBindingFragment
import me.shouheng.vmlib.comn.EmptyViewModel

class SecondFragment : ViewBindingFragment<EmptyViewModel, FragmentSecondBinding>() {

    override fun doCreateView(savedInstanceState: Bundle?) {
        binding.buttonSecond.onDebouncedClick {
            (activity as? FragmentChangeInteraction)?.changeToFirstFragment()
        }
    }
}
