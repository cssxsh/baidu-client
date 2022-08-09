package xyz.cssxsh.baidu.unit.data

import kotlinx.serialization.*

/**
 * 机器人索引
 * @param id 机器⼈ID
 */
@Serializable
public data class ServiceIndex(
    @SerialName("serviceId")
    val id: String
)
