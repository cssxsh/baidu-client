package xyz.cssxsh.baidu.aip.censor

import kotlinx.serialization.*
import kotlinx.serialization.json.*

@Serializable
sealed class CensorResult : CensorStatus {
    abstract val logId: Long
    abstract val isHitMd5: Boolean

    @Serializable
    data class Image(
        @SerialName("log_id")
        override val logId: Long = 0,
        @SerialName("error_code")
        override val errorCode: Long? = null,
        @SerialName("error_msg")
        override val errorMessage: String? = null,
        @SerialName("conclusion")
        override val conclusion: String = "",
        @SerialName("conclusionType")
        override val conclusionType: ConclusionType = ConclusionType.NONE,
        @SerialName("isHitMd5")
        override val isHitMd5: Boolean = false,
        @SerialName("data")
        val `data`: List<Record> = emptyList(),
    ) : CensorResult() {

        @Serializable
        data class Record(
            @SerialName("error_code")
            override val errorCode: Long? = null,
            @SerialName("error_msg")
            override val errorMessage: String? = null,
            @SerialName("conclusion")
            override val conclusion: String,
            @SerialName("conclusionType")
            override val conclusionType: ConclusionType,
            @SerialName("type")
            val type: Int,
            @SerialName("subType")
            val subType: Int,
            @SerialName("msg")
            val message: String,
            @SerialName("datasetName")
            override val datasetName: String,
            @SerialName("probability")
            override val probability: Double,
            @SerialName("stars")
            val stars: List<CensorItem.Star> = emptyList(),
            @SerialName("hits")
            val hits: List<CensorItem.Hit> = emptyList(),
            @SerialName("codes")
            val codes: List<String> = emptyList(),
            @SerialName("results")
            val results: List<JsonObject> = emptyList()
        ) : CensorStatus, CensorItem
    }

    @Serializable
    data class Text(
        @SerialName("log_id")
        override val logId: Long = 0,
        @SerialName("error_code")
        override val errorCode: Long? = null,
        @SerialName("error_msg")
        override val errorMessage: String? = null,
        @SerialName("conclusion")
        override val conclusion: String = "",
        @SerialName("conclusionType")
        override val conclusionType: ConclusionType = ConclusionType.NONE,
        @SerialName("data")
        val `data`: List<Record> = emptyList(),
        @SerialName("isHitMd5")
        override val isHitMd5: Boolean = false
    ) : CensorResult() {

        @Serializable
        data class Record(
            @SerialName("error_code")
            override val errorCode: Long? = null,
            @SerialName("error_msg")
            override val errorMessage: String? = null,
            @SerialName("conclusion")
            override val conclusion: String,
            @SerialName("conclusionType")
            override val conclusionType: ConclusionType,
            @SerialName("type")
            val type: Int,
            @SerialName("subType")
            val subType: Int,
            @SerialName("msg")
            val message: String,
            @SerialName("hits")
            val hits: List<CensorItem.Hit> = emptyList(),
            @SerialName("results")
            val results: List<JsonObject> = emptyList()
        ) : CensorStatus
    }

    @Serializable
    data class Video(
        @SerialName("log_id")
        override val logId: Long = 0,
        @SerialName("error_code")
        override val errorCode: Long? = null,
        @SerialName("error_msg")
        override val errorMessage: String? = null,
        @SerialName("conclusion")
        override val conclusion: String = "",
        @SerialName("conclusionType")
        override val conclusionType: ConclusionType = ConclusionType.NONE,
        @SerialName("frames")
        val frames: List<Frame> = emptyList(),
        @SerialName("isHitMd5")
        override val isHitMd5: Boolean = false,
        @SerialName("conclusionTypeGroupInfos")
        val conclusionTypeGroupInfos: JsonArray = JsonArray(emptyList())
    ) : CensorResult() {

        @Serializable
        data class Frame(
            @SerialName("error_code")
            override val errorCode: Long? = null,
            @SerialName("error_msg")
            override val errorMessage: String? = null,
            @SerialName("conclusion")
            override val conclusion: String,
            @SerialName("conclusionType")
            override val conclusionType: ConclusionType,
            @SerialName("data")
            val `data`: List<Image.Record> = emptyList(),
            @SerialName("frameThumbnailUrl")
            val frameThumbnailUrl: String,
            @SerialName("frameTimeStamp")
            val frameTimeStamp: Int,
            @SerialName("frameUrl")
            val frameUrl: String
        ) : CensorStatus
    }

    @Serializable
    data class Voice(
        @SerialName("log_id")
        override val logId: Long = 0,
        @SerialName("error_code")
        override val errorCode: Long? = null,
        @SerialName("error_msg")
        override val errorMessage: String? = null,
        @SerialName("conclusion")
        override val conclusion: String = "",
        @SerialName("conclusionType")
        override val conclusionType: ConclusionType = ConclusionType.NONE,
        @SerialName("data")
        val `data`: List<Record> = emptyList(),
        @SerialName("isHitMd5")
        override val isHitMd5: Boolean = false,
        @SerialName("rawText")
        val rawText: List<String> = emptyList(),
        @SerialName("sn")
        val sn: String = "",
    ) : CensorResult() {

        @Serializable
        data class Record(
            @SerialName("error_code")
            override val errorCode: Long? = null,
            @SerialName("error_msg")
            override val errorMessage: String? = null,
            @SerialName("conclusion")
            override val conclusion: String,
            @SerialName("conclusionType")
            override val conclusionType: ConclusionType,
            @SerialName("text")
            val text: String,
            @SerialName("auditData")
            val auditData: List<Text.Record> = emptyList()
        ) : CensorStatus
    }
}