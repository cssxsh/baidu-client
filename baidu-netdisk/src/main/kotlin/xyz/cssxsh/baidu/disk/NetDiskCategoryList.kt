package xyz.cssxsh.baidu.disk

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetDiskCategoryList(
    @SerialName("errno")
    val errno: Int,
    @SerialName("info")
    val info: Map<CategoryType, NetDiskCategory>,
    @SerialName("request_id")
    val requestId: RequestIdType
)