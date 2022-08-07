package xyz.cssxsh.baidu.unit.data

import kotlinx.serialization.*
import kotlinx.serialization.json.*

/**
 * @param intents 意图列表
 */
@Serializable
public data class Schema(
    @SerialName("intents")
    val intents: List<Intent> = emptyList(),
) {

    /**
     * @param name 意图名称
     * @param confidence 意图置信度
     * @param info 单轮解析的详细信息
     */
    @Serializable
    public data class Intent(
        @SerialName("intent_name")
        val name: String,
        @SerialName("intent_confidence")
        val confidence: Double,
        @SerialName("slu_info")
        val info: JsonObject? = null,
        @SerialName("slots")
        val slots: List<JsonObject> = emptyList(),
    )
}
