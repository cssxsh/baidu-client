package xyz.cssxsh.baidu.unit.data

import kotlinx.serialization.*

@Serializable
public data class SkillCopyInfo(
    @SerialName("schedule")
    val schedule: String,
    @SerialName("description")
    val description: String
)
