package me.shouheng.dynamic.hook

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.view.View
import me.shouheng.dynamic.resources.DynamicResourcesAwareFragment
import me.shouheng.dynamic.resources.FragmentDynamicResourcesAware

/** Dynamic fragment lifecycle callbacks. */
internal class DynamicFragmentLifecycleCallbacks(
    private val fragmentDynamicResourcesAware: FragmentDynamicResourcesAware
) : FragmentManager.FragmentLifecycleCallbacks() {

    override fun onFragmentViewCreated(
        fm: FragmentManager,
        f: Fragment,
        v: View,
        savedInstanceState: Bundle?
    ) {
        if (f is DynamicResourcesAwareFragment) {
            fragmentDynamicResourcesAware.addDynamicResourcesAwareFragment(f)
        }
    }

    override fun onFragmentViewDestroyed(fm: FragmentManager, f: Fragment) {
        if (f is DynamicResourcesAwareFragment) {
            fragmentDynamicResourcesAware.removeDynamicResourcesAwareFragment(f)
        }
    }
}
