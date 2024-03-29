package xyz.cssxsh.baidu.disk.data

import kotlinx.serialization.*
import xyz.cssxsh.baidu.api.*

/**
 * 网盘关联设备列表
 * @param list 列表
 * @param hasMore 是否还有更多
 */
@Serializable
public data class NetDiskDeviceList(
    @SerialName("list")
    val list: List<NetDiskDevice> = emptyList(),
    @SerialName("title")
    val title: String = "",
    @SerialName("has_more")
    @Serializable(NumberToBooleanSerializer::class)
    val hasMore: Boolean = false,
    @SerialName("errmsg")
    override val errorMessage: String = "",
    @SerialName("errno")
    override val errorNo: Int = 0,
    @SerialName("request_id")
    override val requestId: String
) : NetDiskErrorInfo