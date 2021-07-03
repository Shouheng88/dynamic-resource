package me.shouheng.dynamic.loader

import me.shouheng.dynamic.resources.IResources

/** The resources loader. */
interface ResourcesLoader {

    /** The target source type. */
    fun target(): SourceType

    /**
     * Start to load resources.
     *
     * - [path]      path of resources to load.
     * - [listener] load progress callback.
     */
    fun load(
        path: String,
        listener: ResourcesLoaderListener
    )
}

/** Resources loader listener. */
interface ResourcesLoaderListener {

    /** On start. */
    fun onStart()

    /** On succeed. */
    fun onSucceed(resources: IResources)

    /** On failed with error code and message. */
    fun onFailed(code: String, message: String)
}

const val ERROR_CODE_FILE_NOT_FOUND         = "-1"
const val ERROR_CODE_HOOK_ASSET_MANAGER     = "-2"