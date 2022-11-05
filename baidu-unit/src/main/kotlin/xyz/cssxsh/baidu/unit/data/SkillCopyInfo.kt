package xyz.cssxsh.baidu.unit.data

import kotlinx.serialization.*

/**
 * 复制状态信息
 * @property schedule 复制进度
 * @property description 复制信息描述
 */
@Serializable
public data class SkillCopyInfo(
    @SerialName("schedule")
    val schedule: String,
    @SerialName("description")
    val description: String
)
