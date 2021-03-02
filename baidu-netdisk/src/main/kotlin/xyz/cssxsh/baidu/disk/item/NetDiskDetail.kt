package xyz.cssxsh.baidu.disk.item

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.cssxsh.baidu.NumberToBooleanSerializer
import xyz.cssxsh.baidu.disk.*

@Serializable
data class NetDiskDetail(
    @SerialName("category")
    override val category: CategoryType,
    @SerialName("date_taken")
    val date: Long? = null,
    @SerialName("dlink")
    val link: String? = null,
    @SerialName("filename")
    override val filename: String,
    @SerialName("fs_id")
    override val id: Long,
    @SerialName("height")
    val height: Int? = null,
    @SerialName("isdir")
    @Serializable(with = NumberToBooleanSerializer::class)
    override val isDir: Boolean,
    @SerialName("md5")
    override val md5: String,
    @SerialName("oper_id")
    val operatorId: Long,
    @SerialName("path")
    override val path: String,
    @SerialName("server_ctime")
    override val created: Long,
    @SerialName("server_mtime")
    override val modified: Long,
    @SerialName("size")
    override val size: Long,
    @SerialName("thumbs")
    val thumbs: Map<String, String>? = null,
    @SerialName("width")
    val width: Int? = null,
) : NetDiskFileInfo
