package xyz.cssxsh.baidu.unit.data

import kotlinx.serialization.*

/**
 * 请求信息类型，
 * @property TEXT 文本
 * @property EVENT 事件
 */
@Serializable
public enum class QueryType {
    TEXT, EVENT
}