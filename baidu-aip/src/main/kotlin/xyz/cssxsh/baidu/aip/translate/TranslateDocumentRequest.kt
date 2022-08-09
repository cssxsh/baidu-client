package xyz.cssxsh.baidu.aip.translate

import kotlinx.serialization.*

/**
 * 文档翻译请求数据
 * @param from 源语言
 * @param to 目标语言
 * @param domain 垂直领域
 * @param input 输入的文件内容
 * @param output 输出的文件内容
 */
@Serializable
public data class TranslateDocumentRequest(
    @SerialName("domain")
    val domain: String? = null,
    @SerialName("from")
    val from: String,
    @SerialName("input")
    val input: Input,
    @SerialName("output")
    val output: Output,
    @SerialName("to")
    val to: String
) {

    /**
     * @param filenamePrefix 输出的文件名称，不传则通过默认生成规则生成。
     * @param formats 输出的文件类型，不传则按照默认方案，目前仅支持传一个
     */
    @Serializable
    public data class Output(
        @SerialName("filename_prefix")
        val filenamePrefix: String? = null,
        @SerialName("formats")
        val formats: List<String> = emptyList()
    )

    /**
     * @param content 输入的文件内容 base64 编码后的字符串。大小不超过 50M
     * @param filename 输入的文件名称，不传则随机生成只用于“下载原文”功能时生成的文件名，不会用来使用后缀名进行文件格式判断
     * @param format 输入的文件类型
     */
    @Serializable
    public data class Input(
        @SerialName("content")
        val content: String,
        @SerialName("filename")
        val filename: String? = null,
        @SerialName("format")
        val format: String
    )
}