package xyz.cssxsh.baidu.aip.translate

import kotlinx.serialization.*

/**
 * 翻译任务查询请求数据
 */
@Serializable
public data class TranslateQueryRequest(
    @SerialName("id")
    val id: String
)