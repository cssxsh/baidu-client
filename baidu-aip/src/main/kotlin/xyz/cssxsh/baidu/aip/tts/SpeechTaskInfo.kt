package xyz.cssxsh.baidu.aip.tts

import kotlinx.serialization.*

/**
 * 任务详情
 * @param id 任务ID
 * @param download 任务下载信息
 * @param status 任务状态
 */
@Serializable
public data class SpeechTaskInfo(
    @SerialName("task_id")
    val id: String,
    @SerialName("task_result")
    val download: SpeechTaskDownload? = null,
    @SerialName("task_status")
    val status: SpeechTaskStatus
)