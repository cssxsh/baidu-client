package xyz.cssxsh.baidu.unit.data

import kotlinx.serialization.*

/**
 * 使⽤状态
 * @property INIT
 * @property UNUSED
 * @property IN_USE
 * @property TO_BE_RECYCLED
 * @property RECYCLING
 * @property RECYCLED
 * @property ERROR
 * @property DELETED
 */
@Serializable
public enum class SkillUseStatus {
    @SerialName("init")
    INIT,
    @SerialName("unused")
    UNUSED,
    @SerialName("inuse")
    IN_USE,
    @SerialName("toberecycled")
    TO_BE_RECYCLED,
    @SerialName("recycling")
    RECYCLING,
    @SerialName("recycled")
    RECYCLED,
    @SerialName("error")
    ERROR,
    @SerialName("deleted")
    DELETED
}