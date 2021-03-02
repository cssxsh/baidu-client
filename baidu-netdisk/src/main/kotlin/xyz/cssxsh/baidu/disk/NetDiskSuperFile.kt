package xyz.cssxsh.baidu.disk

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.cssxsh.baidu.RequestIdType

@Serializable
data class NetDiskSuperFile(
    @SerialName("md5")
    val md5: String,
    @SerialName("request_id")
    val requestId: RequestIdType
)