package xyz.cssxsh.baidu.unit.data

import kotlinx.serialization.*

@Serializable
internal data class ResponseBody<T : Any>(
    @SerialName("log_id")
    override val logId: String? = null,
    @SerialName("error_code")
    override val errorCode: Long = 0,
    @SerialName("error_msg")
    override val errorMessage: String? = null,
    @SerialName("result")
    val result: T? = null
) : BaiduUnitExceptionInfo
