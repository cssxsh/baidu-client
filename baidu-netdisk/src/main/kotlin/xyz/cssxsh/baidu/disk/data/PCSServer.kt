package xyz.cssxsh.baidu.disk.data

import kotlinx.serialization.*

/**
 * PCS 服务器信息
 * @param host 域名
 * @param expire 过期时间
 * @param server 服务器
 */
@Serializable
public data class PCSServer(
    @SerialName("client_ip")
    val clientIp: String,
    @SerialName("expire")
    val expire: Long,
    @SerialName("host")
    val host: String,
    @SerialName("server")
    val server: List<String>,
    @SerialName("error_code")
    override val errorCode: Int = 0,
    @SerialName("error_msg")
    override val errorMessage: String = "",
    @SerialName("request_id")
    override val requestId: Long
) : PCSErrorInfo