package xyz.cssxsh.baidu.unit.data

import kotlinx.serialization.*

@Serializable
@Suppress("EnumEntryName")
public enum class SkillCategory {
    /**
     * 开发者⾃定义
     */
    user,
    /**
     * 预置
     */
    built
}