package xyz.cssxsh.baidu.disk.item

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonPrimitive
import xyz.cssxsh.baidu.NumberToBooleanSerializer
import xyz.cssxsh.baidu.disk.*

@Serializable
data class NetDiskPreCreateFile(
    @SerialName("category")
    override val category: CategoryType,
    @SerialName("ctime")
    override val created: Long,
    @SerialName("fs_id")
    override val id: Long,
    @SerialName("isdir")
    @Serializable(with = NumberToBooleanSerializer::class)
    override val isDir: Boolean,
    @SerialName("md5")
    override val md5: String,
    @SerialName("mtime")
    override val modified: Long,
    @SerialName("path")
    override val path: String,
    @SerialName("request_id")
    val requestId: JsonPrimitive? = null,
    @SerialName("size")
    override val size: Long,
    @SerialName("filename")
    override val filename: String = ""
) : NetDiskFileInfo