package xyz.cssxsh.baidu.disk

import kotlinx.serialization.*

@Serializable
data class NetDiskSingleFile(
    @SerialName("ctime")
    val ctime: Int,
    @SerialName("fs_id")
    val fsId: Long,
    @SerialName("md5")
    val md5: String,
    @SerialName("mtime")
    val mtime: Int,
    @SerialName("path")
    val path: String,
    @SerialName("request_id")
    val requestId: Long,
    @SerialName("size")
    val size: Int
)