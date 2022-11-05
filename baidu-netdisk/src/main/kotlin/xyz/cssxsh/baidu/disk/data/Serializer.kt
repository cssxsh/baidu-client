package xyz.cssxsh.baidu.disk.data

import kotlinx.serialization.*
import xyz.cssxsh.baidu.api.*

/**
 * 排序类型
 * @property NAME 按名称
 * @property SIZE 按大小
 * @property TIME 按时间
 */
public enum class OrderType(public val value: String) {
    NAME(value = "name"),
    SIZE(value = "size"),
    TIME(value = "time");

    override fun toString(): String = value
}

/**
 * VIP类型
 * @property ORDINARY 免费用户
 * @property MEMBER 一般会员
 * @property SUPER_MEMBER 超级会员
 *
 * @property updateLimit 上传大小极限
 * @property superLimit 分块大小极限
 * @property transferLimit 转移分享文件数量极限
 */
@Serializable(with = VipType.Serializer::class)
public enum class VipType(public val updateLimit: Long, public val superLimit: Int, public val transferLimit: Int) {
    ORDINARY(4L shl 30, 4 shl 20, 500),
    MEMBER(10L shl 30, 16 shl 20, 3000),
    SUPER_MEMBER(20L shl 30, 32 shl 20, 5000);

    internal companion object Serializer : KSerializer<VipType> by OrdinalSerializer()
}

/**
 * 异步/同步类型
 * @property SYNC 同步
 * @property AUTO 自动
 * @property ASYNC 异步
 */
@Serializable(with = AsyncType.Serializer::class)
public enum class AsyncType {
    SYNC,
    AUTO,
    ASYNC;

    internal companion object Serializer : KSerializer<AsyncType> by OrdinalSerializer()
}

/**
 * 文件分类类型
 * @property NONE 无，可能是文件夹
 * @property VIDEO 视频
 * @property AUDIO 音频
 * @property IMAGE 图片
 * @property DOCUMENT 文档
 * @property APPLICATION 应用
 * @property OTHERS 其他
 * @property BITTORRENT 种子
 */
@Serializable(with = CategoryType.Serializer::class)
public enum class CategoryType {
    NONE,
    VIDEO,
    AUDIO,
    IMAGE,
    DOCUMENT,
    APPLICATION,
    OTHERS,
    BITTORRENT;

    internal companion object Serializer : KSerializer<CategoryType> by OrdinalSerializer()
}

/**
 * 预上传结果
 * @property NONE 失败
 * @property NOT_EXIST 文件不存在
 * @property EXIST 文件已存在
 */
@Serializable(with = PrepareReturnType.Serializer::class)
public enum class PrepareReturnType {
    NONE,
    NOT_EXIST,
    EXIST;

    internal companion object Serializer : KSerializer<PrepareReturnType> by OrdinalSerializer()
}

/**
 * 文件冲突策略
 * @property FAIL 直接返回失败
 * @property NEW_COPY 重命名文件
 * @property OVERWRITE 覆盖
 * @property SKIP 跳过
 */
public enum class OnDupType(public val value: String) {
    FAIL(value = "fail"),
    NEW_COPY(value = "newcopy"),
    OVERWRITE(value = "overwrite"),
    SKIP(value = "skip");

    override fun toString(): String = value
}

/**
 * 任务状态
 * @property PENDING 等待
 * @property RUNNING 运行
 * @property SUCCESS 成功
 * @property FAILURE 失败
 */
@Serializable(with = TaskStatus.Serializer::class)
public enum class TaskStatus {
    PENDING,
    RUNNING,
    SUCCESS,
    FAILURE;

    internal companion object Serializer : KSerializer<TaskStatus> by LowerCaseSerializer()
}