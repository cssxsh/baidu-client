package xyz.cssxsh.baidu.unit.data

import kotlinx.serialization.*
import java.time.LocalDateTime

/**
 * 机器人信息
 * @param id 技能ID
 * @param name 技能名称
 * @param description 技能描述
 * @param category 技能类别
 * @param type 技能类型
 * @param status 技能状态
 * @param version 技能中当前的模型版本
 * @param created 创建时间
 * @param updated 更新时间
 */
@Serializable
public data class ServiceSkillInfo(
    @SerialName("skillId")
    val id: Long,
    @SerialName("skillName")
    val name: String,
    @SerialName("skillDesc")
    val description: String,
    @SerialName("skillCategory")
    val category: SkillCategory,
    @SerialName("skillType")
    val type: SkillType,
    @SerialName("skillStatus")
    val status: SkillStatus,
    @SerialName("skillVersion")
    val version: String,
    @SerialName("createTime")
    @Serializable(LocalDateTimeSerializer::class)
    val created: LocalDateTime,
    @Serializable(LocalDateTimeSerializer::class)
    @SerialName("updateTime")
    val updated: LocalDateTime
)