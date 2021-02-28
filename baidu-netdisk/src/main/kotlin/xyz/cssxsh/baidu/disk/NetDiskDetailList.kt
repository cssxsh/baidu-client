package xyz.cssxsh.baidu.disk

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.cssxsh.baidu.disk.item.NetDiskDetail

@Serializable
data class NetDiskDetailList(
    @SerialName("errmsg")
    val message: String,
    @SerialName("errno")
    val errno: Int,
    @SerialName("list")
    val list: List<NetDiskDetail>,
    @SerialName("names")
    val names: Map<String, String>,
    @SerialName("request_id")
    val requestId: String,
)