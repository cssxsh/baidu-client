package xyz.cssxsh.baidu.unit.data

import kotlinx.serialization.*

@Serializable
public data class ResponseBody(
    @SerialName("log_id")
    val logId: String? = null,
    @SerialName("error_code")
    val errorCode: Long = 0,
    @SerialName("error_msg")
    val errorMessage: String? = null,
    @SerialName("result")
    val result: ResponseData? = null
)
