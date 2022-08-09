package xyz.cssxsh.baidu.aip.translate

import kotlinx.serialization.*

@Serializable
public data class TranslateDocumentTask(
    @SerialName("id")
    val id: String
)