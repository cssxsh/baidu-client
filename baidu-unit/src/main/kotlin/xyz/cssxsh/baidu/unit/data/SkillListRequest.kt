package xyz.cssxsh.baidu.unit.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 技能列表请求
 * @param pageNo 当前⻚码
 * @param pageSize 机器⼈列表
 */
@Serializable
public data class SkillListRequest(
    @SerialName("skillCategory")
    val category: SkillCategory? = null,
    @SerialName("skillType")
    val type: SkillType? = null,
    @SerialName("pageNo")
    val pageNo: Int,
    @SerialName("pageSize")
    val pageSize: Int
)