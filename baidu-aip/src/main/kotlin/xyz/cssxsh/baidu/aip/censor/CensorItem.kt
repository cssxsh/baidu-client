package xyz.cssxsh.baidu.aip.censor

import kotlinx.serialization.*
import kotlinx.serialization.json.*

public sealed interface CensorItem {
    public val datasetName: String
    public val probability: Double

    @Serializable
    public data class Star(
        @SerialName("datasetName")
        override val datasetName: String,
        @SerialName("probability")
        override val probability: Double,
        @SerialName("name")
        val name: List<String>
    ) : CensorItem

    @Serializable
    public data class Hit(
        @SerialName("datasetName")
        override val datasetName: String,
        @SerialName("probability")
        override val probability: Double,
        @SerialName("words")
        val words: List<String> = emptyList(),
        @SerialName("modelHitPositions")
        val modelHitPositions: JsonArray = JsonArray(emptyList()),
        @SerialName("wordHitPositions")
        val wordHitPositions: JsonArray = JsonArray(emptyList())
    ) : CensorItem
}