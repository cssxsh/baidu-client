package xyz.cssxsh.baidu.unit.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 机器⼈技能列表请求
 * @param serviceId 机器⼈ID
 * @param skillId 技能ID
 */
@Serializable
public data class ServiceSkillRequest(
    @SerialName("serviceId")
    val serviceId: String,
    @SerialName("skillId")
    val skillId: Long
)