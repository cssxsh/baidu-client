package xyz.cssxsh.baidu.unit.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 机器⼈列表信息
 * @param totalCount 机器⼈总数
 * @param currentPage 当前⻚码
 * @param services 机器⼈列表
 */
@Serializable
public data class ServiceList(
    @SerialName("totalCount")
    val totalCount: Int,
    @SerialName("currentPage")
    val currentPage: Int,
    @SerialName("services")
    val services: List<ServiceInfo> = emptyList()
)