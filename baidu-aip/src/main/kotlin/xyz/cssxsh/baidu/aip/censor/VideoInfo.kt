package xyz.cssxsh.baidu.aip.censor

import kotlinx.serialization.*

@Serializable
public data class VideoInfo(
    @SerialName("fields")
    val fields: List<Field>,
    @SerialName("subject")
    val subject: String
) {
    @Serializable
    public data class Field(
        @SerialName("title")
        val title: String,
        @SerialName("value")
        val value: String
    )
}