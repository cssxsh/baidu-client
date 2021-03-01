package xyz.cssxsh.baidu.disk


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetDiskOpera(
    @SerialName("errno")
    val errno: Int,
    @SerialName("info")
    val info: List<Item>,
    @SerialName("request_id")
    val requestId: Long,
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