package xyz.cssxsh.baidu.aip.translate

import kotlinx.serialization.*
import xyz.cssxsh.baidu.aip.*

@Serializable
internal data class ResponseBody2<T>(
    @SerialName("log_id")
    override val logId: Long? = null,
    @SerialName("error_code")
    override val errorCode: Long = 0,
    @SerialName("error_msg")
    override val errorMessage: String? = null,
    @SerialName("data")
    val data: T? = null
) : AipExceptionInfo