package xyz.cssxsh.baidu.unit.data

import kotlinx.serialization.*

@Serializable
@Suppress("EnumEntryName")
public enum class ServiceType {

    // region common

    /**
     * 对话分发
     * @see ServiceCategory.common
     */
    dialogueDistribution,
    /**
     * 任务流
     * @see ServiceCategory.common
     */
    taskflow,

    // endregion

    // region dialogueDistribution

    /**
     * 通⽤场景
     * @see ServiceCategory.marketingClue
     */
    general,
    /**
     * 教育场景
     * @see ServiceCategory.marketingClue
     */
    edu,
    /**
     * 相亲场景
     * @see ServiceCategory.marketingClue
     */
    date

    // endregion
}