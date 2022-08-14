package xyz.cssxsh.baidu.aip.translate

import kotlinx.serialization.*
import xyz.cssxsh.baidu.api.*
import java.time.*

/**
 * @param id 任务ID
 * @param from 源语言
 * @param to 目标语言
 * @param domain 垂直领域
 * @param input 输入的文件内容
 * @param output 输出的文件内容
 * @param status 任务状态
 * @param reason 对任务状态的补充说明
 * @param createdAt 文档上传时间
 * @param updatedAt 任务最近一次状态更新时间
 * @param expiredAt 任务过期时间
 */
@Serializable
public data class TranslateDocumentData(
    @SerialName("created_at")
    @Serializable(TimestampSerializer::class)
    val createdAt: OffsetDateTime,
    @SerialName("domain")
    val domain: String = "general",
    @SerialName("expired_at")
    @Serializable(TimestampSerializer::class)
    val expiredAt: OffsetDateTime,
    @SerialName("from")
    val from: String,
    @SerialName("id")
    val id: String,
    @SerialName("input")
    val input: TranslateDocumentFile,
    @SerialName("output")
    val output: Output = Output(),
    @SerialName("reason")
    val reason: String,
    @SerialName("status")
    val status: TranslateStatus,
    @SerialName("to")
    val to: String,
    @SerialName("updated_at")
    @Serializable(TimestampSerializer::class)
    val updatedAt: OffsetDateTime
) {

    @Serializable
    public data class Output(
        @SerialName("files")
        val files: List<TranslateDocumentFile> = emptyList()
    )
}