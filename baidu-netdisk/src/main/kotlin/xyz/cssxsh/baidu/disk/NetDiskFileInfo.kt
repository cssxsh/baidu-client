package xyz.cssxsh.baidu.disk


interface NetDiskFileInfo {
    val category: CategoryType
    val id: Long
    val filename: String
    val isDir: Boolean
    val md5: String?
    val path: String
    val created: Long
    val modified: Long
    val size: Int
}