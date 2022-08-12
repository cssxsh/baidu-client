package xyz.cssxsh.baidu.disk.data

import kotlinx.serialization.*

/**
 * 网盘容量信息
 * @param quota 空间配额，单位为字节。
 * @param used 已使用空间大小，单位为字节。
 */
@Serializable
public data class PCSQuotaInfo(
    @SerialName("quota")
    val quota: Long,
    @SerialName("used")
    val used: Long,
    @SerialName("error_code")
    override val errorCode: Int = 0,
    @SerialName("error_msg")
    override val errorMessage: String = "",
    @SerialName("request_id")
    override val requestId: Long
) : PCSErrorInfo