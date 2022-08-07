package xyz.cssxsh.baidu.unit.data

import kotlinx.serialization.*
import kotlinx.serialization.json.*

/**
 * @param status 状态码，0为正常
 * @param message 错误信息，非零时有效
 * @param origin 应答来自哪个技能（skill_id）或机器人（service_id），注意有些应答可能是机器人给出的（不来自任何一个技能）。
 * @param actions 动作列表
 * @param schema 解析的schema，解析意图、词槽结果都从这里面获取
 * @param sentiment query的情感分析结果
 * @param slu SLU解析
 * @param history 历史词槽结果
 */
@Serializable
public data class ResponseItem(
    @SerialName("status")
    val status: Int = 0,
    @SerialName("msg")
    val message: String = "",
    @SerialName("origin")
    val origin: String = "",
    @SerialName("actions")
    val actions: List<Action> = emptyList(),
    @SerialName("schema")
    val schema: Schema,
    @SerialName("raw_query")
    val raw: String,
    @SerialName("sentiment_analysis")
    val sentiment: JsonObject? = null,
    @SerialName("response")
    val slu: List<JsonObject> = emptyList(),
    @SerialName("slot_history")
    val history: List<JsonObject> = emptyList(),
)
