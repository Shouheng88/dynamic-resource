package me.shouheng.dynamic.utils

import android.text.TextUtils
import java.io.File
import java.io.IOException

/** Dynamic file utils. */
object DynamicFile {

    /** Get file name from file path. */
    fun getNameFromPath(path: String): String {
        if (TextUtils.isEmpty(path)) return ""
        val lastSeparator = path.lastIndexOf(File.separatorChar)
        val extensionPos = path.lastIndexOf('.')
        return if (lastSeparator > extensionPos) "" else path.substring(extensionPos+1)
    }

    fun createOrExistsFile(file: File?): Boolean {
        if (file == null) return false
        if (file.exists()) return file.isFile
        return if (!createOrExistsDir(file.parentFile)) false else try {
            file.createNewFile()
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
    }

    /*------------------------------------- inner methods ---------------------------------------*/
    private fun createOrExistsDir(file: File?): Boolean {
        return file != null && if (file.exists()) file.isDirectory else file.mkdirs()
    }

    fun getFileByPath(filePath: String?): File? {
        return if (isSpace(filePath)) null else File(filePath)
    }

    fun isFileExists(filePath: String?): Boolean {
        return isFileExists(getFileByPath(filePath))
    }

    fun isFileExists(file: File?): Boolean {
        return file != null && file.exists()
    }

    /*----------------------------------normal strings--------------------------------------*/
    fun isSpace(s: String?): Boolean {
        if (s == null) return true
        var i = 0
        val len = s.length
        while (i < len) {
            if (!Character.isWhitespace(s[i])) {
                return false
            }
            ++i
        }
        return true
    }

}
