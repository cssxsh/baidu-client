package xyz.cssxsh.baidu.unit.data

import kotlinx.serialization.*

/**
 * 机器人技能复制状态
 * @property none 无
 * @property success 成功
 * @property failed 失败
 */
@Serializable
@Suppress("EnumEntryName")
public enum class SkillCopyStatus {
    none,
    success,
    failed,
}