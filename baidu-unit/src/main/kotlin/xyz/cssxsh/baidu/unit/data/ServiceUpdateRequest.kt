package xyz.cssxsh.baidu.unit.data

import kotlinx.serialization.*

/**
 * 机器人更新请求
 * @param id 机器⼈ID
 * @param name 机器⼈名称
 * @param description 机器⼈描述
 */
@Serializable
public data class ServiceUpdateRequest(
    @SerialName("serviceId")
    val id: String,
    @SerialName("serviceName")
    val name: String,
    @SerialName("serviceDesc")
    val description: String
)