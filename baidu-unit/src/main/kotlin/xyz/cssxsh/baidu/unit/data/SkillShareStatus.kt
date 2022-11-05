package xyz.cssxsh.baidu.unit.data

import kotlinx.serialization.*

/**
 * 机器人技能分享状态
 * @property NONE 无
 * @property RUNNING 分享中
 * @property STOPPED 已停⽌
 */
@Serializable
public enum class SkillShareStatus {
    @SerialName("none")
    NONE,
    @SerialName("running")
    RUNNING,
    @SerialName("stopped")
    STOPPED,
}