package xyz.cssxsh.baidu.disk.data

import kotlinx.serialization.*

/**
 * 分享记录
 * @param count 记录数
 * @param list 纪录列表
 * @param next 下一页，为 0 时，没有下一页
 */
@Serializable
public data class NetDiskShareList(
    @SerialName("count")
    val count: Int,
    @SerialName("list")
    val list: List<NetDiskShareRecord>,
    @SerialName("nextpage")
    val next: Int,
    @SerialName("show_msg")
    override val errorMessage: String = "",
    @SerialName("errno")
    override val errorNo: Int = 0,
    @SerialName("request_id")
    override val requestId: String
) : NetDiskErrorInfo