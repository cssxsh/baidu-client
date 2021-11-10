package xyz.cssxsh.baidu.disk

import kotlinx.serialization.*

@Serializable
data class NetDiskQuotaInfo(
    @SerialName("errno")
    val errno: Int,
    @SerialName("expire")
    val expire: Boolean = false,
    @SerialName("free")
    val free: Long? = null,
    @SerialName("request_id")
    val requestId: RequestIdType,
    @SerialName("total")
    val total: Long,
    @SerialName("used")
    val used: Long
)