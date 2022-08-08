package xyz.cssxsh.baidu.aip.tts

import kotlinx.serialization.*
import xyz.cssxsh.baidu.aip.SpeechTaskStatus

@Serializable
public data class SpeechTaskInfo(
    @SerialName("task_id")
    val id: String,
    @SerialName("task_result")
    val download: SpeechTaskDownload? = null,
    @SerialName("task_status")
    val status: SpeechTaskStatus
)