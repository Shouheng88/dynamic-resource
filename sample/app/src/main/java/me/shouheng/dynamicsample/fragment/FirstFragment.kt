package me.shouheng.dynamicsample.fragment

import android.os.Bundle
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
    }
}
