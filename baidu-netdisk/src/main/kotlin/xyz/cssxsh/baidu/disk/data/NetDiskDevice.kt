package xyz.cssxsh.baidu.disk.data

import kotlinx.serialization.*
import xyz.cssxsh.baidu.api.*
import java.time.*

@Serializable
public data class NetDiskDevice(
    @SerialName("device_brand")
    val brand: String,
    @SerialName("device_category")
    val category: Int,
    @SerialName("device_id")
    val id: String,
    @SerialName("device_logourl")
    val logo: String,
    @SerialName("device_name")
    val name: String,
    @SerialName("device_provider")
    val provider: String,
    @SerialName("device_type")
    val type: Int,
    @SerialName("extra_info")
    val extraInfo: String,
    @SerialName("guide_desc")
    val description: String,
    @SerialName("guide_imgurl")
    val image: String,
    @SerialName("ctime")
    @Serializable(TimestampSerializer::class)
    val created: OffsetDateTime,
    @SerialName("mtime")
    @Serializable(TimestampSerializer::class)
    val modified: OffsetDateTime,
    @SerialName("status")
    @Serializable(NumberToBooleanSerializer::class)
    val status: Boolean
)