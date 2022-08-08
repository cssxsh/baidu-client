package xyz.cssxsh.baidu.aip.tts

import kotlinx.serialization.*
import xyz.cssxsh.baidu.aip.*

@Serializable
public data class SpeechTask(
    @SerialName("log_id")
    override val logId: Long = 0,
    @SerialName("error_code")
    override val errorCode: Long? = null,
    @SerialName("error_msg")
    override val errorMessage: String? = null,
    @SerialName("task_id")
    val taskId: String? = null,
    @SerialName("task_status")
    val status: SpeechTaskStatus? = null
) : AipExceptionInfo