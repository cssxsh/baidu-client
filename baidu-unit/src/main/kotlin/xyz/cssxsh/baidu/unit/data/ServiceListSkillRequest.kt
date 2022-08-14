package xyz.cssxsh.baidu.unit.data

import kotlinx.serialization.*

/**
 * 机器⼈技能列表请求
 * @param id 机器⼈ID
 * @param pageNo 当前⻚码
 * @param pageSize 机器⼈列表
 */
@Serializable
public data class ServiceListSkillRequest(
    @SerialName("serviceId")
    val id: String,
    @SerialName("pageNo")
    val pageNo: Int,
    @SerialName("pageSize")
    val pageSize: Int
)