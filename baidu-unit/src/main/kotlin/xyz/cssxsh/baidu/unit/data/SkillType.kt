package xyz.cssxsh.baidu.unit.data

import kotlinx.serialization.*

/**
 * 机器人技能类型
 * @property dialogue 对话
 * @property faq 问答
 * @property ddqa ⽂档问答
 * @property kbqa 表格问答
 */
@Serializable
@Suppress("EnumEntryName")
public enum class SkillType {
    dialogue,
    faq,
    ddqa,
    kbqa
}