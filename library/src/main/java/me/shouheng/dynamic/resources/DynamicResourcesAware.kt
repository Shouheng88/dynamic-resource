package me.shouheng.dynamic.resources

import me.shouheng.dynamic.loader.SourceType
import java.lang.ref.ReferenceQueue
import java.lang.ref.WeakReference

/** Dynamic resources aware interface. */
interface DynamicResourcesAware {

    /** Will be called when resources changed. */
    fun onResourcesChanged(resources: IResources, sourceType: SourceType)

    /** Name of the aware.*/
    fun name(): String? = null

}

/** Weak [DynamicResourcesAware] instance. */
class WeakDynamicResourcesAware(
    private val target: String,
    referent: DynamicResourcesAware?,
    q: ReferenceQueue<in DynamicResourcesAware>?
) : WeakReference<DynamicResourcesAware>(referent, q) {

    override fun toString(): String {
        return "[$target]" + super.toString()
    }
}
