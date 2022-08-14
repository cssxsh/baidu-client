package xyz.cssxsh.baidu.unit.data

import kotlinx.serialization.*

/**
 * 机器⼈技能列表信息
 * @param totalCount 技能⼈总数
 * @param currentPage 当前⻚码
 * @param skills 技能列表
 */
@Serializable
public data class ServiceSkillList(
    @SerialName("totalCount")
    val totalCount: Int,
    @SerialName("currentPage")
    val currentPage: Int,
    @SerialName("skills")
    val skills: List<ServiceSkillInfo> = emptyList()
)