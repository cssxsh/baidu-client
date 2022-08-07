package xyz.cssxsh.baidu.unit.data

import kotlinx.serialization.*

/**
 * @param id 动作ID
 * @param say 应答话术
 * @param customReply 用户自定义应答，如果action_type为event，对应事件定义在此处。
 * @param type 动作类型 [ActionType]
 * @param confidence 动作置信度，取值范围[0,100]
 * @param options 选项列表。
 */
@Serializable
public data class Action(
    @SerialName("action_id")
    val id: String,
    @SerialName("say")
    val say: String,
    @SerialName("custom_reply")
    val customReply: String = "",
    @SerialName("type")
    val type: ActionType,
    @SerialName("confidence")
    val confidence: Double,
    @SerialName("options")
    val options: List<Option> = emptyList(),
    @SerialName("img")
    val images: List<String> = emptyList(),
) {

    /**
     * @param text 选项文字
     * @param info 选项细节信息
     */
    @Serializable
    public data class Option(
        @SerialName("option")
        val text: String,
        @SerialName("info")
        val info: Map<String, String> = emptyMap()
    )
}