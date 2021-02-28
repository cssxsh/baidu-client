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
    val dateTaken: Long? = null,
    @SerialName("dlink")
    val link: String? = null,
    @SerialName("filename")
    override val filename: String,
    @SerialName("fs_id")
    override val fsId: Long,
    @SerialName("height")
    val height: Int? = null,
    @SerialName("isdir")
    @Serializable(with = NumberToBooleanSerializer::class)
    override val isDir: Boolean,
    @SerialName("md5")
    override val md5: String,
    @SerialName("oper_id")
    val operatorId: Int,
    @SerialName("path")
    override val path: String,
    @SerialName("server_ctime")
    override val createdTime: Long,
    @SerialName("server_mtime")
    override val modifiedTime: Long,
    @SerialName("size")
    override val size: Int,
    @SerialName("thumbs")
    val thumbs: Map<String, String>? = null,
    @SerialName("width")
    val width: Int? = null,
) : NetDiskFileInfo