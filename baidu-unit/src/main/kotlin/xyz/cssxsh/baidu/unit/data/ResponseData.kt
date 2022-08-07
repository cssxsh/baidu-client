package xyz.cssxsh.baidu.unit.data

import kotlinx.serialization.*
import java.time.LocalDateTime

/**
 * @param version =3.0，当前api版本对应协议版本号为3.0，固定值
 * @param serviceId 机器人ID，同请求参数。
 * @param skillIds 如果请求中传递了 skill_ids ，则响应参数值技能ID顺序按照 skill_ids 的顺序排列
 * @param logId 日志id，
 * @param sessionId 本轮对话后更新的 session_id 信息
 * @param timestamp interaction生成的时间
 * @param refId 每轮对话自动生成的唯一ID
 * @param responses 本轮应答列表。由于请求接口支持请求多个技能，因此这里可能有多个应答。应答列表是有序的，其第一个元素是最为推荐采用的一个应答。
 */
@Serializable
public data class ResponseData(
    @SerialName("version")
    val version: String = "3.0",
    @SerialName("service_id")
    val serviceId: String = "",
    @SerialName("skill_ids")
    val skillIds: List<String> = emptyList(),
    @SerialName("log_id")
    val logId: String? = null,
    @SerialName("session_id")
    val sessionId: String = "",
    @SerialName("context")
    var context: Context? = null,
    @SerialName("timestamp")
    @Serializable(LocalDateTimeSerializer::class)
    val timestamp: LocalDateTime = LocalDateTime.MIN,
    @SerialName("ref_id")
    val refId: String,
    @SerialName("responses")
    val responses: List<ResponseItem> = emptyList(),
)