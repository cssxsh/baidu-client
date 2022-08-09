package xyz.cssxsh.baidu.unit.data

import kotlinx.serialization.*

/**
 * 技能索引
 * @param id 技能ID
 */
@Serializable
public data class SkillIndex(
    @SerialName("skillId")
    val id: Long
)
