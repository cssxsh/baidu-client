package xyz.cssxsh.baidu.unit.data

import kotlinx.serialization.*

@Serializable
@Suppress("EnumEntryName")
public enum class SkillType {
    /**
     * 对话
     */
    dialogue,
    /**
     * 问答
     */
    faq,
    /**
     * ⽂档问答
     */
    ddqa,
    /**
     * 表格问答
     */
    kbqa
}