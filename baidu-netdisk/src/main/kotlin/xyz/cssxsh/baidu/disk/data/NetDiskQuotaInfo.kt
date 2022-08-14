package xyz.cssxsh.baidu.disk.data

import kotlinx.serialization.*

/**
 * 网盘容量信息
 * @param total 总计大小
 * @param used 已使用大小
 * @param free 免费大小
 * @param expire 是否存在7天内过期的容量
 */
@Serializable
public data class NetDiskQuotaInfo(
    @SerialName("expire")
    val expire: Boolean = false,
    @SerialName("free")
    val free: Long = 0,
    @SerialName("total")
    val total: Long,
    @SerialName("used")
    val used: Long,
    @SerialName("show_msg")
    override val errorMessage: String = "",
    @SerialName("errno")
    override val errorNo: Int = 0,
    @SerialName("request_id")
    override val requestId: String
) : NetDiskErrorInfo