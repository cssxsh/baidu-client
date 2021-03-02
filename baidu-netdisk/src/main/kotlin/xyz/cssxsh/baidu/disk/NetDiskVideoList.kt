package xyz.cssxsh.baidu.disk

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.cssxsh.baidu.NumberToBooleanSerializer
import xyz.cssxsh.baidu.RequestIdType
import xyz.cssxsh.baidu.disk.item.NetDiskVideo

@Serializable
data class NetDiskVideoList(
    @SerialName("errno")
    val errno: Int,
    @SerialName("guid")
    val guid: Long,
    @SerialName("guid_info")
    val guidInfo: String,
    @SerialName("info")
    val info: List<NetDiskVideo>,
    @SerialName("request_id")
    val requestId: RequestIdType,
    @SerialName("has_more")
    @Serializable(with = NumberToBooleanSerializer::class)
    val hasMore: Boolean = false
)