package xyz.cssxsh.baidu.unit.data

import kotlinx.serialization.*

@Serializable
@Suppress("EnumEntryName")
public enum class SkillCopyStatus {
    /**
     * 无
     */
    none,
    /**
     * 成功
     */
    success,
    /**
     * 失败
     */
    failed,
}