package xyz.cssxsh.baidu.disk.data

import kotlinx.serialization.*
import xyz.cssxsh.baidu.api.*
import java.time.*

/**
 * 分享详情
 * @param uk 分享者ID
 * @param shareId 分享ID
 * @param period 过期类型，0 永不过期，1 一天后过期，7 七天后过期，30 三十天过期
 * @param params 网页链接参数
 * @param dir 目录
 * @param fid 文件ID
 * @param created 创建时间
 * @param count 文件数量
 * @param status 为 0 时，永久有效, 1, 10 分享过期
 */
@Serializable
public data class NetDiskShortLink(
    @SerialName("appeal_status")
    val status: Int,
    @SerialName("ctime")
    @Serializable(TimestampSerializer::class)
    val created: OffsetDateTime,
    @SerialName("dir")
    val dir: String,
    @SerialName("expired_type")
    val period: Int,
    @SerialName("fcount")
    val count: Int,
    @SerialName("fid")
    val fid: Int,
//    @SerialName("from")
//    val from: Any,
    @SerialName("longurl")
    val params: String,
//    @SerialName("msp")
//    val msp: Int,
    @SerialName("page")
    val page: Int,
//    @SerialName("privilege_type")
//    val privilegeType: Int,
    @SerialName("prod_type")
    val prod: String,
    @SerialName("root")
    @Serializable(NumberToBooleanSerializer::class)
    val root: Boolean,
    @SerialName("shareid")
    val shareId: Long,
//    @SerialName("sharetype")
//    val sharetype: Int,
    @SerialName("third")
    val third: Int,
//    @SerialName("tid")
//    val tid: Any,
    @SerialName("type")
    val type: Int,
    @SerialName("uk")
    val uk: Int,
    @SerialName("uk_str")
    val uid: String,
    @SerialName("view_limit")
    val viewLimit: Int,
    @SerialName("view_visited")
    val viewVisited: Int,
    @SerialName("vipType")
    val vipType: Int,
    @SerialName("show_msg")
    override val errorMessage: String = "",
    @SerialName("errno")
    override val errorNo: Int = 0,
    @SerialName("request_id")
    override val requestId: String = ""
) : NetDiskErrorInfo