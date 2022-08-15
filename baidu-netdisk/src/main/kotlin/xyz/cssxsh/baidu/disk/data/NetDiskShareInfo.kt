package xyz.cssxsh.baidu.disk.data

import kotlinx.serialization.*

/**
 * 分片文件上传结果
 * @param key 允许访问密钥串
 */
@Serializable
public data class NetDiskShareInfo(
    @SerialName("randsk")
    val key: String = "",
    @SerialName("show_msg")
    override val errorMessage: String = "",
    @SerialName("errno")
    override val errorNo: Int = 0,
    @SerialName("request_id")
    override val requestId: String
) : NetDiskErrorInfo