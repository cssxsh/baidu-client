package xyz.cssxsh.baidu.disk.item

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.cssxsh.baidu.NumberToBooleanSerializer
import xyz.cssxsh.baidu.disk.*

@Serializable
data class NetDiskBt(
    @SerialName("category")
    override val category: CategoryType,
    @SerialName("fs_id")
    override val fsId: Long,
    @SerialName("isdir")
    @Serializable(with = NumberToBooleanSerializer::class)
    override val isDir: Boolean,
    @SerialName("local_ctime")
    val localCreatedTime: Long,
    @SerialName("local_mtime")
    val localModifiedTime: Long,
    @SerialName("md5")
    override val md5: String,
    @SerialName("object_key")
    val objectKey: String,
    @SerialName("path")
    override val path: String,
    @SerialName("server_ctime")
    override val createdTime: Long,
    @SerialName("server_filename")
    override val filename: String,
    @SerialName("server_mtime")
    override val modifiedTime: Long,
    @SerialName("share")
    val share: Int,
    @SerialName("size")
    override val size: Int,
    @SerialName("wpfile")
    val wpfile: Int,
) : NetDiskFileInfo