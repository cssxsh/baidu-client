package xyz.cssxsh.baidu.unit.data

import kotlinx.serialization.*

@Serializable
@Suppress("EnumEntryName")
public enum class SkillStatus {
    /**
     * 新建
     */
    new,
    /**
     * 启动中
     */
    starting,
    /**
     * 模型⽣效失败
     */
    failed,
    /**
     * 模型⽣效中
     */
    loading,
    /**
     * 运⾏中
     */
    running,
    /**
     * 停⽌
     */
    stopped
}