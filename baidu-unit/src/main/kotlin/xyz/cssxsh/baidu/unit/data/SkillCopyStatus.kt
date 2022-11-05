package xyz.cssxsh.baidu.unit.data

import kotlinx.serialization.*

/**
 * 机器人技能复制状态
 * @property NONE 无
 * @property SUCCESS 成功
 * @property FAILED 失败
 */
@Serializable
public enum class SkillCopyStatus {
    @SerialName("none")
    NONE,
    @SerialName("success")
    SUCCESS,
    @SerialName("failed")
    FAILED,
}