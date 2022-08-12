package xyz.cssxsh.baidu.unit.data

import kotlinx.serialization.*

/**
 * 机器人技能分享状态
 * @property none 无
 * @property running 分享中
 * @property stopped 已停⽌
 */
@Serializable
@Suppress("EnumEntryName")
public enum class SkillShareStatus {
    none,
    running,
    stopped,
}