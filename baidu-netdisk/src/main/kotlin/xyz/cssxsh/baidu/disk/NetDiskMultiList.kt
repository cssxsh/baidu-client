package xyz.cssxsh.baidu.disk

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonArray
import xyz.cssxsh.baidu.NumberToBooleanSerializer
import xyz.cssxsh.baidu.RequestIdType
import xyz.cssxsh.baidu.disk.item.NetDiskFileOrDir

@Serializable
data class NetDiskMultiList(
    @SerialName("errno")
    val errno: Int,
    @SerialName("guid")
    val guid: Long? = null,
    @SerialName("guid_info")
    val guidInfo: String? = null,
    @SerialName("list")
    val list: List<NetDiskFileOrDir>,
    @SerialName("contentlist")
    private val contentList: JsonArray? = null,
    @SerialName("request_id")
    val requestId: RequestIdType,
    @SerialName("has_more")
    @Serializable(with = NumberToBooleanSerializer::class)
    val hasMore: Boolean = false
)