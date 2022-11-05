package xyz.cssxsh.baidu.unit.data

import kotlinx.serialization.*

/**
 * 技能获得方式
 * @property NEW 新建
 * @property COPY 复制
 * @property QUOTE 问答
 */
@Serializable
public enum class SkillObtainedBy {
    @SerialName("new")
    NEW,
    @SerialName("copy")
    COPY,
    @SerialName("quote")
    QUOTE
}