package xyz.cssxsh.baidu.disk.data

import kotlinx.serialization.*

@Serializable
public data class NetDiskMetaList(
    @SerialName("list")
    val list: List<NetDiskMeta>,
    @SerialName("request_id")
    val requestId: RequestIdType
)