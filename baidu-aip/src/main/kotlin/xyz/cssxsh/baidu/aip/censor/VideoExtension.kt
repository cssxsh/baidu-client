package xyz.cssxsh.baidu.aip.censor

import kotlinx.serialization.*

/**
 * 视频信息
 * @param id 视频在用户平台的唯一ID
 * @param info 用户自定义字段
 */
@Serializable
public data class VideoExtension(
    @SerialName("id")
    val id: String,
    @SerialName("info")
    val info: List<VideoInfo>? = null,
) {

    /**
     * @param subject 主题描述
     * @param fields 字段列表
     */
    @Serializable
    public data class VideoInfo(
        @SerialName("fields")
        val fields: List<Field>,
        @SerialName("subject")
        val subject: String
    )

    /**
     * @param title 字段名称
     * @param value 字段值
     */
    @Serializable
    public data class Field(
        @SerialName("title")
        val title: String,
        @SerialName("value")
        val value: String
    )
}
