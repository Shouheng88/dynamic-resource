package me.shouheng.dynamic.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import me.shouheng.dynamic.R
import me.shouheng.dynamic.databinding.FragmentFirstBinding
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
