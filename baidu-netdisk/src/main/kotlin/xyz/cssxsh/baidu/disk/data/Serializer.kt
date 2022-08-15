package xyz.cssxsh.baidu.disk.data

import kotlinx.serialization.*
import xyz.cssxsh.baidu.api.*

public enum class OrderType(public val value: String) {
    NAME(value = "name"),
    SIZE(value = "size"),
    TIME(value = "time");

    override fun toString(): String = value
}

@Serializable(with = VipType.Serializer::class)
public enum class VipType(public val updateLimit: Long, public val superLimit: Int, public val transferLimit: Int) {
    ORDINARY(4L shl 30, 4 shl 20, 500),
    MEMBER(10L shl 30, 16 shl 20, 3000),
    SUPER_MEMBER(20L shl 30, 32 shl 20, 5000);

    public companion object Serializer : KSerializer<VipType> by OrdinalSerializer()
}

@Serializable(with = AsyncType.Serializer::class)
public enum class AsyncType {
    SYNC,
    AUTO,
    ASYNC;

    public companion object Serializer : KSerializer<AsyncType> by OrdinalSerializer()
}

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

    public companion object Serializer : KSerializer<CategoryType> by OrdinalSerializer()
}

@Serializable(with = PrepareReturnType.Serializer::class)
public enum class PrepareReturnType {
    NONE,
    NOT_EXIST,
    EXIST;

    public companion object Serializer : KSerializer<PrepareReturnType> by OrdinalSerializer()
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

@Serializable(with = TaskStatus.Serializer::class)
public enum class TaskStatus {
    PENDING,
    RUNNING,
    SUCCESS,
    FAILURE;

    public companion object Serializer : KSerializer<TaskStatus> by LowerCaseSerializer()
}