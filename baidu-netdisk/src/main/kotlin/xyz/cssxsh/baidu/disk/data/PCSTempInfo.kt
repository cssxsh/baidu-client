package xyz.cssxsh.baidu.disk.data

import kotlinx.serialization.*

/**
 * 分片文件上传结果
 * @param md5 对应分片的MD5
 */
@Serializable
public data class PCSTempInfo(
    @SerialName("md5")
    val md5: String,
    @SerialName("error_code")
    override val errorCode: Int = 0,
    @SerialName("error_msg")
    override val errorMessage: String = "",
    @SerialName("request_id")
    override val requestId: Long
) : PCSErrorInfo