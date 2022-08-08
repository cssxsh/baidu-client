package xyz.cssxsh.baidu.aip.tts

import kotlinx.serialization.*
import xyz.cssxsh.baidu.aip.*

@Serializable
public data class SpeechTaskDownload(
    @SerialName("err_msg")
    override val errorMessage: String? = null,
    @SerialName("err_no")
    override val errorCode: Long? = null,
    @SerialName("sn")
    val sn: String? = null,
    @SerialName("speech_url")
    val speechUrl: String? = null
) : AipExceptionInfo