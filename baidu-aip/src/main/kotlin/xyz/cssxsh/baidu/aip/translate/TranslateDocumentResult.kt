package xyz.cssxsh.baidu.aip.translate

import kotlinx.serialization.*

@Serializable
public data class TranslateDocumentResult(
    @SerialName("data")
    val `data`: TranslateDocumentData
)