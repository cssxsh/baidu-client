package xyz.cssxsh.baidu.aip.censor

import kotlinx.serialization.*

/**
 * 音频额外信息
 * @param rate 音频采样率
 * @param rawText 是否返回音频识别结果
 * @param split rawText是否拆句
 * @param account 用户信息标识，限长64位字符长度
 * @param audioId 音频信息标识，限长128位字符长度
 */
@Serializable
public data class VoiceExtension(
    val rate: Int = 16_000,
    val rawText: Boolean = true,
    val split: Boolean = true,
    val account: String? = null,
    val audioId: String? = null,
)