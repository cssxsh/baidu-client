package xyz.cssxsh.baidu.disk

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.cssxsh.baidu.disk.item.NetDiskFileOrDir

@Serializable
data class NetDiskMultiList(
    @SerialName("errno")
    val errno: Int,
    @SerialName("guid")
    val guid: Long,
    @SerialName("guid_info")
    val guidInfo: String,
    @SerialName("list")
    val list: List<NetDiskFileOrDir>,
    @SerialName("request_id")
    val requestId: Long
)