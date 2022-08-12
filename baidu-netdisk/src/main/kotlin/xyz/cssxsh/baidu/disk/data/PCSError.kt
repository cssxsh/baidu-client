package xyz.cssxsh.baidu.disk.data

import kotlinx.serialization.*

/**
 * PCS错误信息
 */
@Serializable
public data class PCSError(
    @SerialName("error_code")
    override val errorCode: Int = 0,
    @SerialName("error_msg")
    override val errorMessage: String = "",
    @SerialName("request_id")
    override val requestId: Long
) : PCSErrorInfo