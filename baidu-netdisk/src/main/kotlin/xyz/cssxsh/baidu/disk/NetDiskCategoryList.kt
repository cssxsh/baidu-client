package xyz.cssxsh.baidu.disk

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.cssxsh.baidu.RequestIdType
import xyz.cssxsh.baidu.disk.item.NetDiskCategory

@Serializable
data class NetDiskCategoryList(
    @SerialName("errno")
    val errno: Int,
    @SerialName("info")
    val info: Map<CategoryType, NetDiskCategory>,
    @SerialName("request_id")
    val requestId: RequestIdType
)