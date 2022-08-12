package xyz.cssxsh.baidu.unit.data

import kotlinx.serialization.*

/**
 * 机器人服务类型
 * @property common 通⽤机器⼈
 * @property marketingClue 营销线索机器⼈
 */
@Serializable
@Suppress("EnumEntryName")
public enum class ServiceCategory {
    common,
    marketingClue
}