package xyz.cssxsh.baidu.unit.data

import kotlinx.serialization.*

@Serializable
@Suppress("EnumEntryName")
public enum class SkillUseStatus {
    `init`, unused, inuse, toberecycled, recycling, recycled, error, deleted
}