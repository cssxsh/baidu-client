package xyz.cssxsh.baidu.aip.tts

import kotlinx.serialization.*
import xyz.cssxsh.baidu.aip.AipExceptionInfo

/**
 * 任务结果
 * @param tasks 任务详情
 * @param errorInfo 任务错误信息
 */
@Serializable
public data class SpeechTaskResult(
    @SerialName("log_id")
    override val logId: Long,
    @SerialName("error_code")
    override val errorCode: Long? = null,
    @SerialName("error_msg")
    override val errorMessage: String? = null,
    @SerialName("error_info")
    val errorInfo: List<String>? = null,
    @SerialName("tasks_info")
    val tasks: List<SpeechTaskInfo>? = null,
) : AipExceptionInfo