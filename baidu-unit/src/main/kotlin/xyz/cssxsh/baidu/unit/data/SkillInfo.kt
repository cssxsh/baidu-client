package xyz.cssxsh.baidu.unit.data

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import java.time.LocalDateTime

/**
 * 机器人信息
 * @param id 技能ID
 * @param name 技能名称
 * @param description 技能描述
 * @param category 技能类别
 * @param obtainedBy 技能获取来源
 * @param type 技能类型
 * @param status 技能状态
 * @param version 技能中当前的模型版本
 * @param copy 技能复制状态
 * @param copyInfo 技能复制信息，当技能复制状态⾮none、success时有效
 * @param use 使⽤状态
 * @param instanceId 技能实例id
 * @param share 技能分享状态
 * @param created 创建时间
 * @param updated 更新时间
 */
@Serializable
public data class SkillInfo(
    @SerialName("skillId")
    val id: Long,
    @JsonNames("skillName", "botName")
    val name: String,
    @JsonNames("skillDesc", "botDesc")
    val description: String,
    @JsonNames("skillCategory", "botCategory")
    val category: SkillCategory,
    @JsonNames("skillType", "botType")
    val type: SkillType,
    @SerialName("skillObtainedBy")
    val obtainedBy: SkillObtainedBy,
    @SerialName("skillStatus")
    val status: SkillStatus,
    @JsonNames("skillVersion", "botVersion")
    val version: String,
    @SerialName("copyStatus")
    val copy: SkillCopyStatus = SkillCopyStatus.none,
    @SerialName("copyInfo")
    val copyInfo: SkillCopyInfo? = null,
    @SerialName("shareStatus")
    val share: SkillShareStatus = SkillShareStatus.none,
    @SerialName("createTime")
    @Serializable(LocalDateTimeSerializer::class)
    val created: LocalDateTime,
    @Serializable(LocalDateTimeSerializer::class)
    @SerialName("updateTime")
    val updated: LocalDateTime,
    @SerialName("instanceId")
    val instanceId: String = "",
    @SerialName("useStatus")
    val use: SkillUseStatus = SkillUseStatus.unused,
    @SerialName("fileNum")
    val fileNum: Int = 0,
    @SerialName("fileSize")
    val fileSize: String = "",
)