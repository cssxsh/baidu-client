package xyz.cssxsh.baidu.disk.data

import java.time.*

/**
 * 文件基本信息
 * @property category 分类
 * @property id 文件ID
 * @property filename 文件名
 * @property isDir 是否是目录
 * @property md5 文件MD5 当 chars[9] > 'g' 时，为加密处理的文件md5
 * @property path 文件路径
 * @property created 创建时间
 * @property modified 修改时间
 * @property size 大小
 * @property share 是否为共享
 */
public interface NetDiskFileInfo {
    public val category: CategoryType
    public val id: Long
    public val operatorId: Long
    public val ownerId: Long
    public val filename: String
    public val isDir: Boolean
    public val md5: String
    public val path: String
    public val created: OffsetDateTime
    public val modified: OffsetDateTime
    public val size: Long
    public val share: Boolean
}