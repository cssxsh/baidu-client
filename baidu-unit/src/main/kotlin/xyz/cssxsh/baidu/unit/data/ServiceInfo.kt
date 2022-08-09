package xyz.cssxsh.baidu.unit.data

import kotlinx.serialization.*
import java.time.LocalDateTime

/**
 * 机器人信息
 * @param id 机器⼈ID
 * @param name 机器⼈名称
 * @param description 机器⼈描述
 * @param category 机器⼈类型：common(通⽤机器⼈)、marketingClue(营销线索机器⼈)；默认为common
 * @param type 机器⼈类别：
 * * common(通⽤机器⼈)包括：dialogueDistribution（对话分发）、taskflow;
 * * marketingClue包括：general(通⽤场景)、edu（教育场景）、date（相亲场景）；默认为dialogueDistribution
 * @param created 机器⼈ID
 * @param updated 机器⼈ID
 */
@Serializable
public data class ServiceInfo(
    @SerialName("serviceId")
    val id: String,
    @SerialName("serviceName")
    val name: String,
    @SerialName("serviceDesc")
    val description: String,
    @SerialName("serviceCategory")
    val category: ServiceCategory,
    @SerialName("serviceType")
    val type: ServiceType,
    @SerialName("createTime")
    @Serializable(LocalDateTimeSerializer::class)
    val created: LocalDateTime,
    @Serializable(LocalDateTimeSerializer::class)
    @SerialName("updateTime")
    val updated: LocalDateTime
)