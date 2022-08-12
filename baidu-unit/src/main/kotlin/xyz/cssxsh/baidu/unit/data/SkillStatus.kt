package xyz.cssxsh.baidu.unit.data

import kotlinx.serialization.*

/**
 * 机器人状态
 * @property new 新建
 * @property starting 启动中
 * @property failed 模型⽣效失败
 * @property loading 模型⽣效中
 * @property running 运⾏中
 * @property stopped 停⽌
 */
@Serializable
@Suppress("EnumEntryName")
public enum class SkillStatus {
    new,
    starting,
    failed,
    loading,
    running,
    stopped
}