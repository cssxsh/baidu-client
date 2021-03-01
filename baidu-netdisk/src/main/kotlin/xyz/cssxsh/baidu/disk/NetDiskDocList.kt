package xyz.cssxsh.baidu.disk

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.cssxsh.baidu.NumberToBooleanSerializer
import xyz.cssxsh.baidu.disk.item.NetDiskDoc

@Serializable
data class NetDiskDocList(
    @SerialName("errno")
    val errno: Int,
    @SerialName("guid")
    val guid: Int,
    @SerialName("guid_info")
    val guidInfo: String,
    @SerialName("info")
    val info: List<NetDiskDoc>,
    @SerialName("request_id")
    val requestId: Long,
    @SerialName("has_more")
    @Serializable(with = NumberToBooleanSerializer::class)
    val hasMore: Boolean = false
)