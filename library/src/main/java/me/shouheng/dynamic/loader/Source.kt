package me.shouheng.dynamic.loader

/** The source type. */
interface SourceType

/** Source enums for dynamic resources. */
enum class Source : SourceType {

    /** Resources from assets of App. */
    ASSETS,

    /** Resources from res directory of App. */
    RESOURCES,

    /** Resources from external storage. */
    EXTERNAL
}
