package xyz.cssxsh.baidu.aip.translate

import kotlinx.serialization.*

/**
 * 图片翻译结果
 * @param from 源语种方向
 * @param to 目标语种方向
 * @param content 分段翻译内容
 * @param sumSrc 未分段翻译原文
 * @param sumDst 未分段翻译译文
 * @param pasteImage 图片贴合 (整屏贴合)， paste=1 时有效，base64格式
 */
@Serializable
public data class TranslatePictureResult(
    @SerialName("content")
    val content: List<Content> = emptyList(),
    @SerialName("from")
    val from: String = "",
    @SerialName("to")
    val to: String = "",
    @SerialName("pasteImg")
    val pasteImage: String? = null,
    @SerialName("sumDst")
    val sumDst: String = "",
    @SerialName("sumSrc")
    val sumSrc: String = "",
) {

    /**
     * @param src 识别原文
     * @param dst 翻译译文
     * @param rect 原文擦除矩形位置 格式："rect":"0 0 321 199"矩形的位置信息，依次顺序left, top, wide, high (以图片左上角顶点为坐标原点)
     * @param points 译文贴合矩形位置 （坐标0点为左上角），坐标顺序左上，右上，右下，左下
     * @param lineCount 合并行数 表示该分段信息是原文的多少行合并在一起
     * @param pasteImage 分段贴合图片 paste=2 时有效，base64格式
     */
    @Serializable
    public data class Content(
        @SerialName("dst")
        val dst: String,
        @SerialName("lineCount")
        val lineCount: Int,
        @SerialName("pasteImg")
        val pasteImage: String? = null,
        @SerialName("points")
        val points: List<Point>,
        @SerialName("rect")
        val rect: String,
        @SerialName("src")
        val src: String
    )

    @Serializable
    public data class Point(
        @SerialName("x")
        val x: Int,
        @SerialName("y")
        val y: Int
    )
}