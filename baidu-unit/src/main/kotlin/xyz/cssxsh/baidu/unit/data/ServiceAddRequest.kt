package xyz.cssxsh.baidu.unit.data

import kotlinx.serialization.*

/**
 * 机器人信息
 * @param name 机器⼈名称
 * @param description 机器⼈描述
 * @param category 机器⼈类型：common(通⽤机器⼈)、marketingClue(营销线索机器⼈)；默认为common
 * @param type 机器⼈类别：
 * * common(通⽤机器⼈)包括：dialogueDistribution（对话分发）、taskflow;
 * * marketingClue包括：general(通⽤场景)、edu（教育场景）、date（相亲场景）；默认为dialogueDistribution
 */
@Serializable
public data class ServiceAddRequest(
    @SerialName("serviceName")
    val name: String,
    @SerialName("serviceDesc")
    val description: String,
    @SerialName("serviceCategory")
    val category: ServiceCategory,
    @SerialName("serviceType")
    val type: ServiceType
)