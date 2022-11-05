package xyz.cssxsh.baidu.unit.data

import kotlinx.serialization.*

/**
 * 技能添加请求
 * @param name 技能名称
 * @param description 技能描述
 * @param type 技能类型
 */
@Serializable
public data class SkillAddRequest(
    @SerialName("serviceName")
    val name: String,
    @SerialName("serviceDesc")
    val description: String,
    @SerialName("skillType")
    val type: SkillType = SkillType.DIALOGUE
)