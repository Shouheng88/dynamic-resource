package me.shouheng.dynamic.resources

import android.content.res.AssetFileDescriptor
import android.content.res.ColorStateList
import android.content.res.Resources
import android.content.res.XmlResourceParser
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.annotation.RequiresApi
import android.util.TypedValue
import java.io.InputStream

/** The resources. */
interface IResources {

   fun getIdentifier(name: String, defType: String?): Int

   fun getString(id: Int): String

   fun getString(id: Int, vararg formatArgs: Any?): String

   fun getTextArray(id: Int): Array<CharSequence>

   fun getAnimation(id: Int): XmlResourceParser

   fun getText(id: Int): CharSequence

   fun getText(id: Int, def: CharSequence?): CharSequence

   fun getDrawableForDensity(id: Int, density: Int): Drawable?

   @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
   fun getDrawableForDensity(id: Int, density: Int, theme: Resources.Theme?): Drawable?

   fun getDimensionPixelSize(id: Int): Int

   fun getIntArray(id: Int): IntArray

   fun getQuantityString(id: Int, quantity: Int, vararg formatArgs: Any?): String

   fun getQuantityString(id: Int, quantity: Int): String

   fun getStringArray(id: Int): Array<String>

   fun openRawResourceFd(id: Int): AssetFileDescriptor

   fun getDimension(id: Int): Float

   fun getColorStateList(id: Int): ColorStateList

   fun getColorStateList(id: Int, theme: Resources.Theme?): ColorStateList

   fun getBoolean(id: Int): Boolean

   fun getQuantityText(id: Int, quantity: Int): CharSequence

   fun getColor(id: Int): Int

   fun getColor(id: Int, theme: Resources.Theme?): Int

   fun openRawResource(id: Int): InputStream

   fun openRawResource(id: Int, value: TypedValue?): InputStream

   fun getInteger(id: Int): Int

   fun getDrawable(id: Int): Drawable

   fun getDrawable(id: Int, theme: Resources.Theme?): Drawable

   fun getLayout(id: Int): XmlResourceParser

   @RequiresApi(Build.VERSION_CODES.O)
   fun getFont(id: Int): Typeface

   fun getXml(id: Int): XmlResourceParser
}
