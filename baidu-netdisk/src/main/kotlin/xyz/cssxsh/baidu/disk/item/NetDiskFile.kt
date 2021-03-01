package xyz.cssxsh.baidu.disk.item

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.cssxsh.baidu.NumberToBooleanSerializer
import xyz.cssxsh.baidu.disk.*

@Serializable
data class NetDiskFile(
    @SerialName("category")
    override val category: CategoryType,
    @SerialName("fs_id")
    override val id: Long,
    @SerialName("md5")
    override val md5: String,
    @SerialName("path")
    override val path: String,
    @SerialName("server_ctime")
    override val created: Long,
    @SerialName("server_filename")
    override val filename: String,
    @SerialName("server_mtime")
    override val modified: Long,
    @SerialName("size")
    override val size: Int,
    @SerialName("thumbs")
    val thumbs: Map<String, String>? = null,
    @SerialName("isdir")
    @Serializable(with = NumberToBooleanSerializer::class)
    override val isDir: Boolean = false,
    @SerialName("local_ctime")
    val localCreatedTime: Long? = 0,
    @SerialName("local_mtime")
    val localModifiedTime: Long? = 0,
) : NetDiskFileInfo