package xyz.cssxsh.baidu.unit.data

import kotlinx.serialization.*

/**
 * 机器⼈技能列表请求
 * @param serviceId 机器⼈ID
 * @param skillIds 技能ID列表
 */
@Serializable
public data class ServiceSkillsRequest(
    @SerialName("serviceId")
    val serviceId: String,
    @SerialName("skillIds")
    val skillIds: List<Long>
)