package xyz.cssxsh.baidu.unit.data

import kotlinx.serialization.*

/**
 * 机器人服务类型
 * @property dialogueDistribution 对话分发 [ServiceCategory.common]
 * @property taskflow 任务流 [ServiceCategory.common]
 * @property taskflow 通⽤场景 [ServiceCategory.marketingClue]
 * @property edu 教育场景 [ServiceCategory.marketingClue]
 * @property date 相亲场景 [ServiceCategory.marketingClue]
 */
@Serializable
@Suppress("EnumEntryName")
public enum class ServiceType {
    dialogueDistribution,
    taskflow,
    general,
    edu,
    date
}