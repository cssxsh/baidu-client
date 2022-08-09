package xyz.cssxsh.baidu.unit.data

import kotlinx.serialization.*

/**
 * 技能更新请求
 * @param id 技能ID
 * @param name 技能名称
 * @param description 技能描述
 */
@Serializable
public data class SkillUpdateRequest(
    @SerialName("skillId")
    val id: String,
    @SerialName("skillName")
    val name: String,
    @SerialName("skillDesc")
    val description: String
)