package xyz.cssxsh.baidu.disk

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.cssxsh.baidu.NumberToBooleanSerializer

@Serializable
data class NetDiskCreateFile(
    @SerialName("category")
    override val category: CategoryType,
    @SerialName("ctime")
    override val createdTime: Long,
    @SerialName("errno")
    val errno: Int,
    @SerialName("fs_id")
    override val fsId: Long,
    @SerialName("isdir")
    @Serializable(with = NumberToBooleanSerializer::class)
    override val isDir: Boolean,
    @SerialName("md5")
    override val md5: String = "",
    @SerialName("mtime")
    override val modifiedTime: Long,
    @SerialName("name")
    val name: String,
    @SerialName("status")
    val status: Int? = null,
    @SerialName("path")
    override val path: String,
    @SerialName("server_filename")
    override val filename: String = "",
    @SerialName("size")
    override val size: Int = 0
) : NetDiskFileInfo