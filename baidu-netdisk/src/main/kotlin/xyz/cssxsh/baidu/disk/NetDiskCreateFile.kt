package xyz.cssxsh.baidu.disk

import kotlinx.serialization.*
import xyz.cssxsh.baidu.*

@Serializable
data class NetDiskCreateFile(
    @SerialName("category")
    override val category: CategoryType,
    @SerialName("ctime")
    override val created: Long = 0,
    @SerialName("errno")
    val errno: Int,
    @SerialName("fs_id")
    override val id: Long = 0,
    @SerialName("isdir")
    @Serializable(with = NumberToBooleanSerializer::class)
    override val isDir: Boolean = false,
    @SerialName("md5")
    override val md5: String = "",
    @SerialName("mtime")
    override val modified: Long = 0,
    @SerialName("name")
    val name: String = "",
    @SerialName("status")
    val status: Int? = null,
    @SerialName("path")
    override val path: String = "",
    @SerialName("server_filename")
    override val filename: String = "",
    @SerialName("size")
    override val size: Long = 0
) : NetDiskFileInfo