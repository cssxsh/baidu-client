package xyz.cssxsh.baidu.unit.data

import kotlinx.serialization.*

/**
 * 机器人服务类型
 * @property DIALOGUE_DISTRIBUTION 对话分发 [ServiceCategory.COMMON]
 * @property TASKFLOW 任务流 [ServiceCategory.COMMON]
 * @property TASKFLOW 通⽤场景 [ServiceCategory.MARKETING_CLUE]
 * @property EDU 教育场景 [ServiceCategory.MARKETING_CLUE]
 * @property DATE 相亲场景 [ServiceCategory.MARKETING_CLUE]
 */
@Serializable
public enum class ServiceType {
    @SerialName("dialogueDistribution")
    DIALOGUE_DISTRIBUTION,
    @SerialName("taskflow")
    TASKFLOW,
    @SerialName("general")
    GENERAL,
    @SerialName("edu")
    EDU,
    @SerialName("date")
    DATE
}