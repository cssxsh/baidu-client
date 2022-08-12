package xyz.cssxsh.baidu.disk.data

import kotlinx.serialization.*

@Serializable
public data class PCSFileChange(
    @SerialName("from")
    val from: String,
    @SerialName("to")
    val to: String
)