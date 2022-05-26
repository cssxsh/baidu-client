package xyz.cssxsh.baidu.disk.data

import kotlinx.serialization.*

@Serializable
public data class NetDiskSuperFile(
    @SerialName("md5")
    val md5: String,
    @SerialName("request_id")
    val requestId: RequestIdType
)