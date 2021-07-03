package me.shouheng.dynamicsample.fragment

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import me.shouheng.dynamicsample.R
import me.shouheng.dynamicsample.databinding.FragmentSecond2Binding
import me.shouheng.utils.ktx.onDebouncedClick
import me.shouheng.vmlib.base.ViewBindingFragment
import me.shouheng.vmlib.comn.EmptyViewModel

class Second2Fragment : ViewBindingFragment<EmptyViewModel, FragmentSecond2Binding>() {

    override fun doCreateView(savedInstanceState: Bundle?) {
        binding.buttonSecond.onDebouncedClick {
            findNavController().navigate(R.id.action_Second2Fragment_to_FirstFragment)
        }
    }
}
