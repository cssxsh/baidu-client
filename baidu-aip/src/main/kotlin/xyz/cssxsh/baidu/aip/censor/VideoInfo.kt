package xyz.cssxsh.baidu.aip.censor

import kotlinx.serialization.*

@Serializable
data class VideoInfo(
    @SerialName("fields")
    val fields: List<Field>,
    @SerialName("subject")
    val subject: String
) {
    @Serializable
    data class Field(
        @SerialName("title")
        val title: String,
        @SerialName("value")
        val value: String
    )
}