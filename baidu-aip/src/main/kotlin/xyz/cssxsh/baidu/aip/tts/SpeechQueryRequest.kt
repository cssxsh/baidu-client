package xyz.cssxsh.baidu.aip.tts

import kotlinx.serialization.*

/**
 * @see taskIds 要查询的任务id
 */
@Serializable
public data class SpeechQueryRequest(
    @SerialName("task_ids")
    val taskIds: List<String>
)