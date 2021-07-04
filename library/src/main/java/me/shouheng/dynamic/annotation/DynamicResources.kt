package me.shouheng.dynamic.annotation

/** The dynamic resource annotation for activity. */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class DynamicResources (
    val enable: Boolean = true
)
