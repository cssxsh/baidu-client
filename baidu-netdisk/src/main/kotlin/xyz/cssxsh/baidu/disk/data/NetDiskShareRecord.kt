package xyz.cssxsh.baidu.disk.data

import kotlinx.serialization.*
import xyz.cssxsh.baidu.api.*
import java.time.*

/**
 * 分享记录详情
 * @param shareId 分享ID
 * @param period 过期类型，0 永不过期，1 一天后过期，7 七天后过期，30 三十天过期
 * @param link 网页链接
 * @param shorturl surl 参数
 * @param password 密码，有可能为空
 * @param files 文件列表
 * @param created 创建时间
 * @param expired 过期时间
 * @param transferred 转存数量
 * @param downloaded 下载数量
 * @param viewed 浏览数量
 * @param public 是否为公开分享
 * @param status 为 0 时，永久有效, 1, 10 分享过期
 * @param category 文件类型
 * @param title 分享标题，未过期时是文件路径
 * @param channel 分享类型
 * @param description 简介
 * @param isELink 是否是免密码链接
 */
@Serializable
public data class NetDiskShareRecord(
    @SerialName("shareId")
    val shareId: Long,
    @SerialName("expiredType")
    val period: Int,
    @SerialName("shortlink")
    val link: String,
    @SerialName("shorturl")
    val shorturl: String,
    @SerialName("passwd")
    val password: String,
    @SerialName("fsIds")
    val files: List<Long>,
    @SerialName("ctime")
    @Serializable(TimestampSerializer::class)
    val created: OffsetDateTime,
    @SerialName("expiredTime")
    @Serializable(TimestampSerializer::class)
    val expired: OffsetDateTime = OffsetDateTime.MIN,
    @SerialName("tCnt")
    val transferred: Int,
    @SerialName("dCnt")
    val downloaded: Int,
    @SerialName("vCnt")
    val viewed: Int,
    @SerialName("public")
    @Serializable(NumberToBooleanSerializer::class)
    val `public`: Boolean,
    @SerialName("status")
    val status: Int,
    @SerialName("typicalCategory")
    val category: CategoryType,
    @SerialName("typicalPath")
    val title: String,
    @SerialName("appId")
    val appId: Int,
    @SerialName("bitmap")
    val bitmap: Int,
    @SerialName("channel")
    val channel: Int,
//    @SerialName("channelInfo")
//    val channelInfo: String,
    @SerialName("description")
    val description: String,
//    @SerialName("dtime")
//    val dtime: Int,
//    @SerialName("ip")
//    val ip: Long,
//    @SerialName("is_card")
//    val isCard: Int,
    @SerialName("isElink")
    @Serializable(NumberToBooleanSerializer::class)
    val isELink: Boolean = false,
//    @SerialName("name")
//    val name: String,
//    @SerialName("port")
//    val port: Int,
//    @SerialName("privilege_type")
//    val privilegeType: Int,
//    @SerialName("publicChannel")
//    val publicChannel: Int,
//    @SerialName("shareinfo")
//    val shareinfo: String,
//    @SerialName("sharetype")
//    val sharetype: Int,
//    @SerialName("sinfo")
//    val sinfo: String,
//    @SerialName("substatus")
//    val substatus: String,
//    @SerialName("tag")
//    val tag: Int,
//    @SerialName("tplId")
//    val tplId: Int
)