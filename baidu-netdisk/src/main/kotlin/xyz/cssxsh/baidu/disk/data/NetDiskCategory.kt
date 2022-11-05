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

    /**
     * 分类详情
     * @param count 数量
     * @param size 大小
     * @param total 总计
     */
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