package xyz.cssxsh.baidu.disk.item

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.cssxsh.baidu.NumberToBooleanSerializer
import xyz.cssxsh.baidu.disk.*

@Serializable
data class NetDiskFileOrDir(
    @SerialName("category")
    override val category: CategoryType,
    @SerialName("dir_empty")
    @Serializable(with = NumberToBooleanSerializer::class)
    val dirEmpty: Boolean? = null,
    @SerialName("empty")
    val empty: Int? = null,
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
    override val md5: String? = null,
    @SerialName("oper_id")
    val operatorId: Int,
    @SerialName("path")
    override val path: String,
    @SerialName("pl")
    val pl: Int,
    @SerialName("server_atime")
    val accessTime: Int,
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
    @SerialName("thumbs")
    val thumbs: Map<String, String>? = null,
    @SerialName("unlist")
    val unlist: Int,
    @SerialName("wpfile")
    val wpfile: Int,
) : NetDiskFileInfo