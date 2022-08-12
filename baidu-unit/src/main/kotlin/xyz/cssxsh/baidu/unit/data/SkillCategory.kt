package xyz.cssxsh.baidu.unit.data

import kotlinx.serialization.*

/**
 * 机器人技能类型
 * @property user 开发者⾃定义
 * @property built 预置
 */
@Serializable
@Suppress("EnumEntryName")
public enum class SkillCategory {
    user,
    built
}