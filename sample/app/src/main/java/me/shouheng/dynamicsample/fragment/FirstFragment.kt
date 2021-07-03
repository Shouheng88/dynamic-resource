package me.shouheng.dynamicsample.fragment

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import me.shouheng.dynamicsample.R
import me.shouheng.dynamicsample.databinding.FragmentFirstBinding
import me.shouheng.utils.ktx.onDebouncedClick
import me.shouheng.vmlib.base.ViewBindingFragment
import me.shouheng.vmlib.comn.EmptyViewModel

class FirstFragment : ViewBindingFragment<EmptyViewModel, FragmentFirstBinding>() {

    override fun doCreateView(savedInstanceState: Bundle?) {
        binding.buttonFirst.onDebouncedClick {
            findNavController().navigate(R.id.action_FirstFragment_to_Second2Fragment)
        }
    }
}
