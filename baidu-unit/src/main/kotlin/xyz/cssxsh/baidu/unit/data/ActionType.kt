package xyz.cssxsh.baidu.unit.data

import kotlinx.serialization.Serializable

@Serializable
@Suppress("EnumEntryName")
public enum class ActionType {
    /**
     * 澄清
     */
    clarify,

    /**
     * 满足
     */
    satisfy,

    /**
     * 引导到对话意图
     */
    guide,

    /**
     * 引导到问答意图
     */
    faqguide,

    /**
     * 理解达成，注：内部使用
     */
    understood,

    /**
     * 理解失败
     */
    failure,

    /**
     * 聊天话术
     */
    chat,

    /**
     * 触发事件，在答复型对话回应,中选择了"执行函数"，将返回event类型的action
     */
    event
}