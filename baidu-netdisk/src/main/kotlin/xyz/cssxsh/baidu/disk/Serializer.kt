package xyz.cssxsh.baidu.disk

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import xyz.cssxsh.baidu.*

public enum class OrderType(public val value: String) {
    NAME(value = "name"),
    SIZE(value = "size"),
    TIME(value = "time");

    override fun toString(): String = value
}

public enum class RenameType {
    NO,
    PATH,
    BLOCK,
    COVER;
}

@Serializable(with = VipType.Serializer::class)
public enum class VipType(public val updateLimit: Long, public val superLimit: Int) {
    ORDINARY(4L shl 30, 4 shl 20),
    MEMBER(10L shl 30, 16 shl 20),
    SUPER_MEMBER(20L shl 30, 32 shl 20);

    public companion object Serializer : KSerializer<VipType> by OrdinalSerializer()
}

@Serializable(with = AsyncType.Serializer::class)
public enum class AsyncType {
    SYNC,
    AUTO,
    ASYNC;

    public companion object Serializer : KSerializer<AsyncType> by OrdinalSerializer()
}

/**
 * [document](https://pan.baidu.com/union/document/basic#%E8%8E%B7%E5%8F%96%E6%96%87%E4%BB%B6%E5%88%97%E8%A1%A8)
 */
@Serializable(with = CategoryType.Serializer::class)
public enum class CategoryType {
    NONE,
    VIDEO,
    AUDIO,
    PICTURES,
    DOCUMENTS,
    APPLICATIONS,
    OTHERS,
    SEEDS;

    public companion object Serializer : KSerializer<CategoryType> by OrdinalSerializer()
}

@Serializable(with = CreateReturnType.Serializer::class)
public enum class CreateReturnType {
    TEMP_,
    NOT_EXIST,
    EXIST;

    public companion object Serializer : KSerializer<CreateReturnType> by OrdinalSerializer()
}

@Serializable(with = FileOnDupType.Serializer::class)
public enum class FileOnDupType {
    FAIL,
    NEWCOPY,
    OVERWRITE,
    SKIP;

    override fun toString(): String = name.lowercase()

    public companion object Serializer : KSerializer<FileOnDupType> by LowerCaseSerializer()
}

@Serializable
public sealed class FileOpera(public val name: String) {

    @Serializable
    public data class Item(
        @SerialName("path")
        val path: String,
        @SerialName("dest")
        val dest: String,
        @SerialName("newname")
        val new: String = "",
        @SerialName("ondup")
        val type: FileOnDupType = FileOnDupType.SKIP
    )

    public data class Copy(
        @SerialName("list")
        val list: List<Item>
    ) : FileOpera(name = "copy")

    public data class Move(
        @SerialName("list")
        val list: List<Item>
    ) : FileOpera(name = "mover")

    public data class Rename(
        @SerialName("list")
        val list: List<Item>
    ) : FileOpera(name = "rename")

    public data class Delete(
        @SerialName("list")
        val list: List<String>
    ) : FileOpera(name = "delete")
}

public typealias RequestIdType = JsonPrimitive