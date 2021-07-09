package me.shouheng.dynamic.store

import android.content.Context
import me.shouheng.dynamic.loader.SourceType

/**
 * The storage for [SourceType]. Called when source type changed and when
 * launch the framework to get the current source type.
 */
interface ISourceTypeStore {

    /** Get the source type. */
    fun getSourceType(): SourceType

    /** Save source type. */
    fun saveSourceType(sourceType: SourceType)

    /** Get the source path. */
    fun getSourcePath(): String

    /** Save the source path. */
    fun saveSourcePath(path: String)
}

/** Default source type store. */
class DefaultSourceTypeStore(
    val context: Context
) : ISourceTypeStore {

    override fun getSourceType(): SourceType {
        val name = context.getSharedPreferences(
            SHARED_PREFERENCES_FILE_NAME, Context.MODE_PRIVATE
        ).getString(SHARED_PREFERENCES_SOURCE_TYPE, null) ?: SourceType.DEFAULT.name
        return SourceType.valueOf(name)
    }

    override fun saveSourceType(sourceType: SourceType) {
        context.getSharedPreferences(
            SHARED_PREFERENCES_FILE_NAME, Context.MODE_PRIVATE
        ).edit().putString(SHARED_PREFERENCES_SOURCE_TYPE, sourceType.name).apply()
    }

    override fun getSourcePath(): String {
        return context.getSharedPreferences(
            SHARED_PREFERENCES_FILE_NAME, Context.MODE_PRIVATE
        ).getString(SHARED_PREFERENCES_SOURCE_TYPE_PATH, null) ?: ""
    }

    override fun saveSourcePath(path: String) {
        context.getSharedPreferences(
            SHARED_PREFERENCES_FILE_NAME, Context.MODE_PRIVATE
        ).edit().putString(SHARED_PREFERENCES_SOURCE_TYPE_PATH, path).apply()
    }

    companion object {
        const val SHARED_PREFERENCES_FILE_NAME = "dynamic_resources"
        const val SHARED_PREFERENCES_SOURCE_TYPE = "source_type"
        const val SHARED_PREFERENCES_SOURCE_TYPE_PATH = "source_type_path"
    }
}
