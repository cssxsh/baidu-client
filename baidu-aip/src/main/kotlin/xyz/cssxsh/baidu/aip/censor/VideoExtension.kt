package xyz.cssxsh.baidu.aip.censor

import kotlinx.serialization.*

@Serializable
public data class VideoExtension(
    @SerialName("id")
    val id: String? = null,
    @SerialName("info")
    val info: List<VideoInfo>? = null,
) {
    @Serializable
    public data class VideoInfo(
        @SerialName("fields")
        val fields: List<Field>,
        @SerialName("subject")
        val subject: String
    )

    @Serializable
    public data class Field(
        @SerialName("title")
        val title: String,
        @SerialName("value")
        val value: String
    )
}
