package me.shouheng.dynamic.utils

import android.text.TextUtils
import java.io.File

/** Dynamic file utils. */
object DynamicFile {

    /** Get file name from file path. */
    fun getNameFromPath(path: String): String {
        if (TextUtils.isEmpty(path)) return ""
        val lastSeparator = path.lastIndexOf(File.separatorChar)
        val extensionPos = path.lastIndexOf('.')
        return if (lastSeparator > extensionPos) "" else path.substring(extensionPos+1)
    }
}
