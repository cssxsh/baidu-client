package xyz.cssxsh.baidu.disk.data

import kotlinx.serialization.*

/**
 * 分类统计信息
 * @param info 统计信息，键是类别，值是统计结果
 */
@Serializable
public data class NetDiskCategory(
    @SerialName("info")
    val info: Map<CategoryType, Detail>,
    @SerialName("errmsg")
    override val errorMessage: String = "",
    @SerialName("errno")
    override val errorNo: Int = 0,
    @SerialName("request_id")
    override val requestId: String
) : NetDiskErrorInfo {

    @Serializable
    public data class Detail(
        @SerialName("count")
        val count: Long,
        @SerialName("size")
        val size: Long,
        @SerialName("total")
        val total: Long
    )
}