package xyz.cssxsh.baidu.disk.item

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetDiskCategory(
    @SerialName("count")
    val count: Long,
    @SerialName("real_server_mtime_2")
    private val realServerMtime2: Long,
    @SerialName("size")
    val size: Long,
    @SerialName("total")
    val total: Long
)