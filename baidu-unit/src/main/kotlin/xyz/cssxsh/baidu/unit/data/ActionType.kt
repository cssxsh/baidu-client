package xyz.cssxsh.baidu.unit.data

import kotlinx.serialization.*

/**
 * 行为类型
 * @property CLARIFY 澄清
 * @property SATISFY 满足
 * @property GUIDE 引导到对话意图
 * @property FAQ_GUIDE 引导到问答意图
 * @property UNDERSTOOD 理解达成，注：内部使用
 * @property FAILURE 理解失败
 * @property CHAT 聊天话术
 * @property EVENT 触发事件，在答复型对话回应,中选择了"执行函数"，将返回event类型的action
 */
@Serializable
public enum class ActionType {
    @SerialName("clarify")
    CLARIFY,
    @SerialName("satisfy")
    SATISFY,
    @SerialName("guide")
    GUIDE,
    @SerialName("faqguide")
    FAQ_GUIDE,
    @SerialName("understood")
    UNDERSTOOD,
    @SerialName("failure")
    FAILURE,
    @SerialName("chat")
    CHAT,
    @SerialName("event")
    EVENT
}