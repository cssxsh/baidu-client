package xyz.cssxsh.baidu.disk


public interface NetDiskFileInfo {
    public val category: CategoryType
    public val id: Long
    public val filename: String
    public val isDir: Boolean
    public val md5: String?
    public val path: String
    public val created: Long
    public val modified: Long
    public val size: Long
}