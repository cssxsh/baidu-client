package xyz.cssxsh.baidu.disk

import xyz.cssxsh.baidu.disk.data.*
import java.time.*

/**
 * 文件查询选项
 * @param path 起始目录, `/` 网盘根目录, 为空时是 APP 数据目录
 * @param order 排序属性
 * @param desc 排序方式
 * @param recursion 递归
 * @param created 最晚创建时间 (此规则只在 all 方法中有用）
 * @param modified 最晚修改时间 (此规则只在 all 方法中有用）
 */
public data class NetDiskOption(
    val path: String,
    val order: OrderType,
    val desc: Boolean,
    val recursion: Boolean,
    val created: OffsetDateTime? = null,
    val modified: OffsetDateTime? = null,
    val formats: List<String>? = null
)
