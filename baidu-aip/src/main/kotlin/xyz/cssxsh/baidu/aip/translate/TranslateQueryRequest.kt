package xyz.cssxsh.baidu.aip.translate

import kotlinx.serialization.*

@Serializable
public data class TranslateQueryRequest(
    @SerialName("id")
    val id: String
)