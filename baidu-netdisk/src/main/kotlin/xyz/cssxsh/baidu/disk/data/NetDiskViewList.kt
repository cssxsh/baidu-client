package xyz.cssxsh.baidu.disk.data

import kotlinx.serialization.*
import xyz.cssxsh.baidu.api.*
import java.time.*

/**
 * 分享文件浏览信息
 * @param shareId 分享ID
 * @param uk 分享者ID
 * @param list 文件列表
 * @param time 分享时间
 * @param status 申诉状态
 * @param title 标题
 * @param fromId 该链接生成平台
 * @param expired 过期类型，0 永不过期，1 一天后过期，7 七天后过期，30 三十天过期
 */
@Serializable
public data class NetDiskViewList(
    @SerialName("share_id")
    val shareId: Long = 0,
    @SerialName("uk")
    val uk: Long = 0,
    @SerialName("list")
    val list: List<NetDiskFile> = emptyList(),
    @SerialName("server_time")
    @Serializable(TimestampSerializer::class)
    val time: OffsetDateTime = OffsetDateTime.MIN,
    @SerialName("appeal_status")
    val status: Int,
    @SerialName("title")
    val title: String = "",
    @SerialName("cfrom_id")
    val fromId: Int,
    @SerialName("expired_type")
    val expired: Int = -1,
    @SerialName("hitrisk")
    @Serializable(NumberToBooleanSerializer::class)
    val isRisk: Boolean = false,
    @SerialName("is_zombie")
    @Serializable(NumberToBooleanSerializer::class)
    val isZombie: Boolean = false,
    @SerialName("errmsg")
    override val errorMessage: String = "",
    @SerialName("errno")
    override val errorNo: Int = 0,
    @SerialName("request_id")
    override val requestId: String
) : NetDiskErrorInfo