package xyz.cssxsh.baidu.disk

import kotlinx.serialization.*

@Serializable
public data class NetDiskCategoryList(
    @SerialName("errno")
    val errno: Int,
    @SerialName("info")
    val info: Map<CategoryType, NetDiskCategory>,
    @SerialName("request_id")
    val requestId: RequestIdType
)