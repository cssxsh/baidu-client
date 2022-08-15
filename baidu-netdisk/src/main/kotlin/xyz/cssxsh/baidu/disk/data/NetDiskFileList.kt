package xyz.cssxsh.baidu.disk.data

import kotlinx.serialization.*
import xyz.cssxsh.baidu.api.*

/**
 * @param list 文件列表
 * @param cursor 下一次查询的起点
 * @param hasMore 是否还有数据
 */
@Serializable
public data class NetDiskFileList(
    @SerialName("cursor")
    val cursor: Int = 0,
    @SerialName("has_more")
    @Serializable(NumberToBooleanSerializer::class)
    val hasMore: Boolean = false,
    @SerialName("list")
    override val list: List<NetDiskFile> = emptyList(),
    @SerialName("errmsg")
    override val errorMessage: String = "",
    @SerialName("errno")
    override val errorNo: Int = 0,
    @SerialName("request_id")
    override val requestId: String
) : NetDiskErrorInfo, NetDiskFileData