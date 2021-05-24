package xyz.cssxsh.baidu.disk

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetDiskMetaList(
    @SerialName("list")
    val list: List<NetDiskMeta>,
    @SerialName("request_id")
    val requestId: RequestIdType
)