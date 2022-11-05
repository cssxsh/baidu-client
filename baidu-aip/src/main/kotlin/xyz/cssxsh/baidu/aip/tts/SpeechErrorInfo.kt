package xyz.cssxsh.baidu.aip.tts

import kotlinx.serialization.*
import xyz.cssxsh.baidu.aip.*

/**
 * 语音错误信息
 */
@Serializable
public data class SpeechErrorInfo(
    @SerialName("err_msg")
    override val errorMessage: String,
    @SerialName("err_no")
    override val errorCode: Long,
    @SerialName("idx")
    val idx: Int? = null,
    @SerialName("sn")
    val sn: String? = null
) : AipExceptionInfo