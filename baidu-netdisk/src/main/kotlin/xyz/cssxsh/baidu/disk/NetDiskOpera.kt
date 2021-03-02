package xyz.cssxsh.baidu.disk


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.cssxsh.baidu.RequestIdType

@Serializable
data class NetDiskOpera(
    @SerialName("errno")
    val errno: Int,
    @SerialName("info")
    val info: List<Item>,
    @SerialName("request_id")
    val requestId: RequestIdType,
    @SerialName("taskid")
    val taskId: Long? = null
) {
    @Serializable
    data class Item(
        @SerialName("errno")
        val errno: Int,
        @SerialName("path")
        val path: String
    )
}