package xyz.cssxsh.baidu.aip.tts

import kotlinx.serialization.*

@Serializable
public data class SpeechErrorInfo(
    @SerialName("err_msg")
    val message: String,
    @SerialName("err_no")
    val no: Int,
    @SerialName("idx")
    val idx: Int? = null,
    @SerialName("sn")
    val sn: String? = null
)