package xyz.cssxsh.baidu.unit.data

import kotlinx.serialization.*

/**
 * @param skills ...
 * 1. 如果本次请求没有携带context.SYS_REMEMBERED_SKILLS，则默认进行全技能多轮对话；
 * 2. 如果本次请求携带了context.SYS_REMEMBERED_SKILLS，则仅对该部分技能进行多轮对话，其余技能则作为首轮进行对话。传递空列表表示全部技能都作为首轮进行对话。
 * 3. taskflow 型机器人所有技能均为单轮对话，该字段无效。
 * @param hist 本字段主要用于保存多轮闲聊技能的历史对话信息,最多保存7轮对话
 * 1. 如果用户不传递context.SYS_CHAT_HIST字段，则默认使用系统保存的闲聊对话历史进行多轮对话；
 * 2. 如果用户传递context.SYS_CHAT_HIST，则需要按照["问句1","答句1"..."问句n"]的方式，传递闲聊对话历史，最多传递7轮，传入的最后一个问句需要与query一致。
 * 3. 普通版闲聊不支持多轮，该字段不生效。
 */
@Serializable
public data class Context(
    @SerialName("SYS_REMEMBERED_SKILLS")
    var skills: List<String> = emptyList(),
    @SerialName("SYS_CHAT_HIST")
    var hist: List<String> = emptyList(),
    @SerialName("SYS_VARS")
    var values: Map<String, String> = emptyMap()
)