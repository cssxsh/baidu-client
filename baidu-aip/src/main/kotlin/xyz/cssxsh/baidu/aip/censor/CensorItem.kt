package xyz.cssxsh.baidu.aip.censor

import kotlinx.serialization.*
import kotlinx.serialization.json.*

/**
 * 审核细项信息
 * @property datasetName 数据集
 * @property probability 概率
 */
public sealed interface CensorItem {
    public val datasetName: String
    public val probability: Double

    /**
     * 审核细项信息-明星
     * @property name 名字
     */
    @Serializable
    public data class Star(
        @SerialName("datasetName")
        override val datasetName: String = "",
        @SerialName("probability")
        override val probability: Double = 1.0,
        @SerialName("name")
        val name: List<String> = emptyList()
    ) : CensorItem

    /**
     * 审核细项信息-关键词
     * @property words 关键词
     */

    @Serializable
    public data class Hit(
        @SerialName("datasetName")
        override val datasetName: String = "",
        @SerialName("probability")
        override val probability: Double = 1.0,
        @SerialName("words")
        val words: List<String> = emptyList(),
        @SerialName("modelHitPositions")
        val modelHitPositions: JsonArray? = null,
        @SerialName("wordHitPositions")
        val wordHitPositions: JsonArray? = null
    ) : CensorItem
}