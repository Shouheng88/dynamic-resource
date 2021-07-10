package me.shouheng.dynamicsample.base

import android.viewbinding.ViewBinding
import me.shouheng.dynamic.annotation.DynamicResources
import me.shouheng.vmlib.base.BaseViewModel
import me.shouheng.vmlib.base.ViewBindingActivity

@DynamicResources(enable = true)
abstract class ParentActivity<U : BaseViewModel, T : ViewBinding> : ViewBindingActivity<U, T>()
