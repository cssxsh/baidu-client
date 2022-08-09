package xyz.cssxsh.baidu.unit.data

import kotlinx.serialization.*

@Serializable
@Suppress("EnumEntryName")
public enum class ServiceCategory {
    /**
     * 通⽤机器⼈
     */
    common,
    /**
     * 营销线索机器⼈
     */
    marketingClue
}