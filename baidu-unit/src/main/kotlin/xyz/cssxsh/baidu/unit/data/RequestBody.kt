package xyz.cssxsh.baidu.unit.data

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import java.time.LocalDateTime

/**
 * @param version =3.0，当前api版本对应协议版本号为3.0，固定值
 * @param serviceId 机器人ID，service_id 与skill_ids不能同时缺失，至少一个有值。
 * @param skillIds 技能ID列表。我们允许开发者指定调起哪些技能。这个列表是有序的——排在越前面的技能，优先级越高。技能优先级体现在response的排序上。
 * @param logId 开发者需要在客户端生成的唯一id，用来定位请求，响应中会返回该字段。对话中每轮请求都需要一个log_id
 * @param sessionId session保存机器人的历史会话信息，由机器人创建，客户端从上轮应答中取出并直接传递，不需要了解其内容。如果为空，则表示清空session（开发者判断用户意图已经切换且下一轮会话不需要继承上一轮会话中的词槽信息时可以把session置空，从而进行新一轮的会话）。
 * @param context 希望在多技能对话过程中贯穿的全局性上下文. 这里预留了一个key用于控制各技能的session记忆。
 * @param request 本轮请求体。
 */
@Serializable
public data class RequestBody(
    @Required
    @SerialName("version")
    var version: String = "3.0",
    @SerialName("service_id")
    var serviceId: String = "",
    @SerialName("skill_ids")
    var skillIds: List<String> = emptyList(),
    @SerialName("log_id")
    var logId: String,
    @SerialName("session_id")
    var sessionId: String,
    @SerialName("context")
    var context: Context? = null,
    @SerialName("request")
    var request: Request,
) {


    /**
     * @param terminalId 与技能对话的终端用户id（如果客户端是用户未登录状态情况下对话的，也需要尽量通过其他标识（比如设备id）来唯一区分用户），
     * 方便今后在平台的日志分析模块定位分析问题、从用户维度统计分析相关对话情况。
     * @param query 本轮请求query（用户说的话）
     * @param info 本轮请求query的附加信息
     * @param options 用于在多轮中实现多选一的对话效果
     * @param updates 干预信息
     * @param hyper 控制相关技能/机器人内部行为的的超参数
     */
    @Serializable
    public data class Request(
        @SerialName("terminal_id")
        var terminalId: String,
        @SerialName("query")
        var query: String,
        @SerialName("query_info")
        var info: QueryInfo? = null,
        @SerialName("client_options")
        var options: List<ClientOption>? = null,
        @SerialName("updates")
        var updates: List<Update>? = null,
        @SerialName("hyper")
        var hyper: HyperParams? = null,
    )

    /**
     * @param type 请求信息类型，取值范围："TEXT","EVENT"。
     * @param source 请求信息来源，可选值："ASR","KEYBOARD"。ASR为语音输入，KEYBOARD为键盘文本输入。
     * @param candidates 请求信息来源若为ASR，该字段为ASR候选信息。
     */
    @Serializable
    public data class QueryInfo(
        @SerialName("type")
        var type: String, // TODO enum
        @SerialName("source")
        var source: String, // TODO enum
        @SerialName("asr_candidates")
        var candidates: List<Candidate>? = null,
    )

    /**
     * @param text 语音输入候选文本
     * @param confidence 语音输入候选置信度，取值范围[0,100]
     */
    @Serializable
    public data class Candidate(
        @SerialName("text")
        var text: String,
        @SerialName("confidence")
        var confidence: Double,
    )

    /**
     * @param attributes 一组kv，记录该候选的若干属性及属性值
     * @param slotUpdates 用户提供的候选项所对应的词槽，即选择成功以后会加入到解析结果中的词槽信息
     */
    @Serializable
    public data class ClientOption(
        @SerialName("text")
        var attributes: Map<String, String>,
        @SerialName("slot_updates")
        var slotUpdates: Map<String, String>,
    )

    /**
     * @param type 干预方式，支持「DEFINE」和「MODIFY」两种方式。
     * 其中「DEFINE」表明抛弃系统解析结果，转而由updates字段来定义；
     * MODIFY」表明在已解析的结果上进行修改
     * @param operations 干预操作集。
     */
    @Serializable
    public data class Update(
        @SerialName("type")
        var type: String,
        @SerialName("ops")
        var operations: JsonArray,
    )

    /**
     * @param sluTags 用于限定slu的解析范围，只在打上了指定tag的意图、或问答对的范围内执行slu
     * @param dynamicSlots 针对特定词槽启用『动态词典』机制，key为词槽名（如user_xxx），value为针对该词槽启用的动态词典id（可以同时启用多个动态词典）。
     * @param localTime 当前当地时间，用于计算例如：“3小时后”此类问法的基准时间。为空默认北京时间。参数格式示例：2020-9-22T11:11:11
     * @param chatReqCnt 闲聊技能中使用--控制返回的回复数目，取值范围1～10
     * @param chatDefaultBotProfile 闲聊技能中使用--是否启用系统画像服务，0:关闭；1:打开
     * @param chatServiceStrict 闲聊技能中使用--是否开启儿童聊天模式，0:关闭；1:打开
     * @param chatCustomBotProfile 闲聊中使用--是否启用自定义画像（仅支持增强版闲聊，启用自定义画像时，需关闭系统画像服务，否则不生效），0:关闭，1:打开
     */
    @Serializable
    public data class HyperParams(
        @SerialName("slu_tags")
        var sluTags: List<String>? = null,
        @SerialName("dynamic_slots")
        var dynamicSlots: List<String>? = null,
        @SerialName("local_time")
        @Serializable(LocalDateTimeSerializer::class)
        var localTime: LocalDateTime? = null,
        @SerialName("chat_req_cnt")
        var chatReqCnt: Boolean? = null,
        @SerialName("chat_default_bot_profile")
        var chatDefaultBotProfile: Boolean? = null,
        @SerialName("chat_service_strict")
        var chatServiceStrict: Boolean? = null,
        @SerialName("chat_custom_bot_profile")
        var chatCustomBotProfile: Boolean? = null,
    )
}
