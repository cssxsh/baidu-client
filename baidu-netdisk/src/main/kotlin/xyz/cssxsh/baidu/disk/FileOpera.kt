package xyz.cssxsh.baidu.disk

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class FileOpera(val name: String) {
    data class Copy(
        @SerialName("list")
        val list: List<Item>
    ) : FileOpera(name = "copy") {
        @Serializable
        data class Item(
            @SerialName("path")
            val path: String,
            @SerialName("dest")
            val dest: String,
            @SerialName("newname")
            val new: String,
            @SerialName("ondup")
            val type: FileOnDupType
        )
    }

    data class Move(
        @SerialName("list")
        val list: List<Item>
    ) : FileOpera(name = "mover") {
        @Serializable
        data class Item(
            @SerialName("path")
            val path: String,
            @SerialName("dest")
            val dest: String,
            @SerialName("newname")
            val new: String,
            @SerialName("ondup")
            val type: FileOnDupType
        )
    }

    data class Rename(
        @SerialName("list")
        val list: List<Item>
    ) : FileOpera(name = "rename") {
        @Serializable
        data class Item(
            @SerialName("path")
            val path: String,
            @SerialName("newname")
            val new: String,
        )
    }

    data class Delete(
        @SerialName("list")
        val list: List<String>
    ) : FileOpera(name = "delete")
}
