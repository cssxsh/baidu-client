package xyz.cssxsh.baidu.disk.item

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetDiskCategory(
    @SerialName("count")
    val count: Int,
    @SerialName("real_server_mtime_2")
    private val realServerMtime2: String,
    @SerialName("size")
    val size: Int,
    @SerialName("total")
    val total: Int
)