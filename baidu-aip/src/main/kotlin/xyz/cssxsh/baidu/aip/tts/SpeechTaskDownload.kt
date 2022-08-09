package xyz.cssxsh.baidu.aip.tts

import kotlinx.serialization.*
import xyz.cssxsh.baidu.aip.*

/**
 * 任务下载信息
 * @param speechUrl 音频下载链接，任务完成后储存72小时
 */
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