package xyz.cssxsh.baidu.unit.data

import kotlinx.serialization.*

/**
 * 机器人服务类型
 * @property COMMON 通⽤机器⼈
 * @property MARKETING_CLUE 营销线索机器⼈
 */
@Serializable
public enum class ServiceCategory {
    @SerialName("common")
    COMMON,
    @SerialName("marketingClue")
    MARKETING_CLUE
}