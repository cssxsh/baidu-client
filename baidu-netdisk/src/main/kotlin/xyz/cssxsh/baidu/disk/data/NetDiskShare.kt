package xyz.cssxsh.baidu.disk.data

import kotlinx.serialization.*
import xyz.cssxsh.baidu.api.*
import java.time.*

/**
 * 分享详情
 * @param shareId 分享ID
 * @param period 过期类型，0 永不过期，1 一天后过期，7 七天后过期，30 三十天过期
 * @param link 网页链接
 * @param created 创建时间
 * @param expired 过期时间
 */
@Serializable
public data class NetDiskShare(
    @SerialName("shareid")
    val shareId: Long = 0,
//    @SerialName("aheadmsg")
//    val aheadmsg: String,
//    @SerialName("createsharetips_ldlj")
//    val createsharetipsLdlj: String,
    @SerialName("ctime")
    @Serializable(TimestampSerializer::class)
    val created: OffsetDateTime = OffsetDateTime.MIN,
    @SerialName("expiredType")
    val period: Int = 0,
    @SerialName("expiretime")
    @Serializable(TimestampSerializer::class)
    val expired: OffsetDateTime = OffsetDateTime.MIN,
    @SerialName("imagetype")
    val imageType: Int = 0,
    @SerialName("link")
    val link: String = "",
//    @SerialName("newno")
//    val newno: String,
    @SerialName("premis")
    val premis: Boolean = true,
    @SerialName("prompt_type")
    val promptType: Int = 0,
    @SerialName("qrcodeurl")
    val qrcode: String = "",
    @SerialName("shorturl")
    val shorturl: String = "",
//    @SerialName("tailmsg")
//    val tailmsg: String,
    @SerialName("show_msg")
    override val errorMessage: String = "",
    @SerialName("errno")
    override val errorNo: Int = 0,
    @SerialName("request_id")
    override val requestId: String
) : NetDiskErrorInfo