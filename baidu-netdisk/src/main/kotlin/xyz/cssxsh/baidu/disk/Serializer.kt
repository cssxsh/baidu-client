package xyz.cssxsh.baidu.disk

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonPrimitive
import xyz.cssxsh.baidu.*

enum class OrderType(val value: String) {
    NAME(value = "name"),
    SIZE(value = "size"),
    TIME(value = "time");

    override fun toString(): String = value
}

enum class RenameType {
    NO,
    PATH,
    BLOCK,
    COVER;
}

@Serializable(with = VipType.Serializer::class)
enum class VipType {
    ORDINARY,
    MEMBER,
    SUPER_MEMBER;

    companion object Serializer : KSerializer<VipType> by OrdinalSerializer()
}

@Serializable(with = AsyncType.Serializer::class)
enum class AsyncType {
    SYNC,
    AUTO,
    ASYNC;

    companion object Serializer : KSerializer<AsyncType> by OrdinalSerializer()
}

/**
 * [document](https://pan.baidu.com/union/document/basic#%E8%8E%B7%E5%8F%96%E6%96%87%E4%BB%B6%E5%88%97%E8%A1%A8)
 */
@Serializable(with = CategoryType.Serializer::class)
enum class CategoryType {
    NONE,
    VIDEO,
    AUDIO,
    PICTURES,
    DOCUMENTS,
    APPLICATIONS,
    OTHERS,
    SEEDS;

    companion object Serializer : KSerializer<CategoryType> by OrdinalSerializer()
}

@Serializable(with = CreateReturnType.Serializer::class)
enum class CreateReturnType {
    TEMP_,
    NOT_EXIST,
    EXIST;

    companion object Serializer : KSerializer<CreateReturnType> by OrdinalSerializer()
}

@Serializable(with = FileOnDupType.Serializer::class)
enum class FileOnDupType {
    FAIL,
    NEWCOPY,
    OVERWRITE,
    SKIP;

    override fun toString(): String = name.toLowerCase()

    companion object Serializer : KSerializer<FileOnDupType> by LowerCaseSerializer()
}

@Serializable
sealed class FileOpera(val name: String) {

    @Serializable
    data class Item(
        @SerialName("path")
        val path: String,
        @SerialName("dest")
        val dest: String,
        @SerialName("newname")
        val new: String = "",
        @SerialName("ondup")
        val type: FileOnDupType = FileOnDupType.SKIP
    )

    data class Copy(
        @SerialName("list")
        val list: List<Item>
    ) : FileOpera(name = "copy")

    data class Move(
        @SerialName("list")
        val list: List<Item>
    ) : FileOpera(name = "mover")

    data class Rename(
        @SerialName("list")
        val list: List<Item>
    ) : FileOpera(name = "rename")

    data class Delete(
        @SerialName("list")
        val list: List<String>
    ) : FileOpera(name = "delete")
}

typealias RequestIdType = JsonPrimitive