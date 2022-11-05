package xyz.cssxsh.baidu.aip.censor

import kotlinx.serialization.*
import kotlinx.serialization.json.*

/**
 * 审核结果数据
 * @property records 审核结果集
 */
@Serializable
public sealed class CensorResult : CensorStatus {
    public abstract val records: List<CensorStatus>
    public abstract val isHitMd5: Boolean

    /**
     * 图片审核结果数据
     * @param data 图片审核结果集
     */
    @Serializable
    @SerialName("Image")
    public data class Image(
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
        override val records: List<Record> get() = data

        /**
         * 图片审核审核记录
         * @property message 信息
         * @property stars 明星相关
         * @property hits 词汇相关
         */
        @Serializable
        public data class Record(
            @SerialName("error_code")
            override val errorCode: Long? = null,
            @SerialName("error_msg")
            override val errorMessage: String? = null,
            @SerialName("conclusion")
            override val conclusion: String = "",
            @SerialName("conclusionType")
            override val conclusionType: ConclusionType = ConclusionType.NONE,
            @SerialName("type")
            val type: Int,
            @SerialName("subType")
            val subType: Int,
            @SerialName("msg")
            val message: String,
            @SerialName("datasetName")
            override val datasetName: String = "",
            @SerialName("probability")
            override val probability: Double = 1.0,
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

    /**
     * 文本审核结果数据
     * @param data 文本审核结果集
     */
    @Serializable
    @SerialName("Text")
    public data class Text(
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
        val `data`: List<Record> = emptyList()
    ) : CensorResult() {
        override val records: List<Record> get() = data

        /**
         * 文本审核记录
         * @property message 信息
         * @property hits 词汇相关
         */
        @Serializable
        public data class Record(
            @SerialName("error_code")
            override val errorCode: Long? = null,
            @SerialName("error_msg")
            override val errorMessage: String? = null,
            @SerialName("conclusion")
            override val conclusion: String = "",
            @SerialName("conclusionType")
            override val conclusionType: ConclusionType = ConclusionType.NONE,
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

    /**
     * 视频审核结果数据
     * @param frames 视频审核结果集
     * @param conclusionTypeGroupInfos 视频审核详细信息
     */
    @Serializable
    @SerialName("Video")
    public data class Video(
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
        val conclusionTypeGroupInfos: JsonArray? = null
    ) : CensorResult() {
        override val records: List<Frame> get() = frames

        /**
         * 视频审核记录
         * @param data 图片审核结果集
         * @param frameThumbnailUrl 截图URL
         * @param frameTimeStamp 时间戳
         * @param frameUrl 截图URL
         */
        @Serializable
        public data class Frame(
            @SerialName("error_code")
            override val errorCode: Long? = null,
            @SerialName("error_msg")
            override val errorMessage: String? = null,
            @SerialName("conclusion")
            override val conclusion: String = "",
            @SerialName("conclusionType")
            override val conclusionType: ConclusionType = ConclusionType.NONE,
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

    /**
     * 语音审核结果数据
     * @param data 语音审核结果集
     * @param rawText 语音转录热文本
     */
    @Serializable
    @SerialName("Voice")
    public data class Voice(
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
        override val records: List<Record> get() = data

        /**
         * 语音审核审核记录
         * @property text 转录文本
         * @property auditData 文本审核结果集
         */
        @Serializable
        public data class Record(
            @SerialName("error_code")
            override val errorCode: Long? = null,
            @SerialName("error_msg")
            override val errorMessage: String? = null,
            @SerialName("conclusion")
            override val conclusion: String = "",
            @SerialName("conclusionType")
            override val conclusionType: ConclusionType = ConclusionType.NONE,
            @SerialName("text")
            val text: String,
            @SerialName("auditData")
            val auditData: List<Text.Record> = emptyList()
        ) : CensorStatus
    }
}