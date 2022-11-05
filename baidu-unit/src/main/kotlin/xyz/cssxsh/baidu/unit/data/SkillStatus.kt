package xyz.cssxsh.baidu.unit.data

import kotlinx.serialization.*

/**
 * 机器人状态
 * @property NEW 新建
 * @property STARTING 启动中
 * @property FAILED 模型⽣效失败
 * @property LOADING 模型⽣效中
 * @property RUNNING 运⾏中
 * @property STOPPED 停⽌
 */
@Serializable
public enum class SkillStatus {
    @SerialName("new")
    NEW,
    @SerialName("starting")
    STARTING,
    @SerialName("failed")
    FAILED,
    @SerialName("loading")
    LOADING,
    @SerialName("running")
    RUNNING,
    @SerialName("stopped")
    STOPPED
}