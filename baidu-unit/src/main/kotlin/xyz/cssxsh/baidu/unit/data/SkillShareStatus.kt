package xyz.cssxsh.baidu.unit.data

import kotlinx.serialization.*

@Serializable
@Suppress("EnumEntryName")
public enum class SkillShareStatus {
    /**
     * 无
     */
    none,
    /**
     * 分享中
     */
    running,
    /**
     * 已停⽌
     */
    stopped,
}