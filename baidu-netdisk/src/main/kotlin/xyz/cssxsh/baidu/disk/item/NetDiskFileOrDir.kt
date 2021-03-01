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
    override val id: Long,
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
    private val pl: Int? = null,
    @SerialName("server_atime")
    val accessed: Long? = null,
    @SerialName("server_ctime")
    override val created: Long,
    @SerialName("server_filename")
    override val filename: String,
    @SerialName("server_mtime")
    override val modified: Long,
    @SerialName("share")
    private val share: Int? = null,
    @SerialName("size")
    override val size: Int,
    @SerialName("thumbs")
    val thumbs: Map<String, String>? = null,
    @SerialName("unlist")
    private val unlist: Int? = null,
    @SerialName("wpfile")
    private val wpfile: Int? = null,
    @SerialName("extent_tinyint1")
    private val extentTinyint1: Int? = null
) : NetDiskFileInfo