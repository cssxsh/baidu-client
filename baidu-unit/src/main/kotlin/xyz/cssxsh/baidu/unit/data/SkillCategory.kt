package xyz.cssxsh.baidu.unit.data

import kotlinx.serialization.*

/**
 * 机器人技能类型
 * @property USER 开发者⾃定义
 * @property BUILT 预置
 */
@Serializable
public enum class SkillCategory {
    @SerialName("user")
    USER,
    @SerialName("built")
    BUILT
}