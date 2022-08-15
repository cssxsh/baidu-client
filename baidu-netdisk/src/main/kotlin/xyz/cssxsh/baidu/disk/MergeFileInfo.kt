package xyz.cssxsh.baidu.disk

import kotlinx.serialization.*
import xyz.cssxsh.baidu.api.*
import java.time.*

/**
 * 合并信息
 * @param blocks 文件各分片MD5数组的json串, 可本地计算或者在 [BaiduPersonalCloudStorage.temp] 的返回值中获得
 * @param uploadId 上传ID
 * @param size 文件大小
 * @param path 文件路径
 */
@Serializable
public data class MergeFileInfo(
    @SerialName("blocks")
    val blocks: List<String>,
    @SerialName("uploadId")
    val uploadId: String,
    @SerialName("size")
    val size: Long,
    @SerialName("path")
    val path: String,
    @SerialName("local_ctime")
    @Serializable(TimestampSerializer::class)
    val created: OffsetDateTime? = null,
    @SerialName("local_mtime")
    @Serializable(TimestampSerializer::class)
    val modified: OffsetDateTime? = null,
)