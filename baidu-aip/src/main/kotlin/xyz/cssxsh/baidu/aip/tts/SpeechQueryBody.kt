package xyz.cssxsh.baidu.aip.tts

import kotlinx.serialization.*

@Serializable
public data class SpeechQueryBody(
    @SerialName("task_ids")
    val taskIds: List<String>
)