package xyz.cssxsh.baidu.disk.data

import kotlinx.serialization.*
import xyz.cssxsh.baidu.api.*

/**
 * 分类文件列表
 * @param list 文件列表
 * @param hasMore 还有下一页
 */
@Serializable
public data class NetDiskCategoryList(
    @SerialName("info")
    override val list: List<NetDiskFile> = emptyList(),
    @SerialName("has_more")
    @Serializable(NumberToBooleanSerializer::class)
    val hasMore: Boolean = false,
    @SerialName("errmsg")
    override val errorMessage: String = "",
    @SerialName("errno")
    override val errorNo: Int = 0,
    @SerialName("request_id")
    override val requestId: String
) : NetDiskErrorInfo, NetDiskFileData