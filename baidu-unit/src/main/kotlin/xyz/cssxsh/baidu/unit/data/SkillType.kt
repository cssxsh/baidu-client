package xyz.cssxsh.baidu.unit.data

import kotlinx.serialization.*

/**
 * 机器人技能类型
 * @property DIALOGUE 对话
 * @property FAQ 问答
 * @property DD_QA ⽂档问答
 * @property KB_QA 表格问答
 */
@Serializable
public enum class SkillType {
    @SerialName("dialogue")
    DIALOGUE,
    @SerialName("faq")
    FAQ,
    @SerialName("ddqa")
    DD_QA,
    @SerialName("kbqa")
    KB_QA
}