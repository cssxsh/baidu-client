package xyz.cssxsh.baidu.disk

import xyz.cssxsh.baidu.disk.CategoryType

interface NetDiskFileInfo {
    val category: CategoryType
    val fsId: Long
    val filename: String
    val isDir: Boolean
    val md5: String?
    val path: String
    val createdTime: Long
    val modifiedTime: Long
    val size: Int
}