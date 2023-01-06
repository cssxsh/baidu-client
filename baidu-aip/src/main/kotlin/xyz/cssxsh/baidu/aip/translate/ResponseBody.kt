package xyz.cssxsh.baidu.aip.translate

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import xyz.cssxsh.baidu.aip.*

@PublishedApi
@Serializable
internal data class ResponseBody<T>(
    @SerialName("log_id")
    override val logId: Long? = null,
    @SerialName("error_code")
    override val errorCode: Long = 0,
    @SerialName("error_msg")
    override val errorMessage: String? = null,
    @OptIn(ExperimentalSerializationApi::class)
    @JsonNames("result", "data")
    val result: T? = null
) : AipExceptionInfo