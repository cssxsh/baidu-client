package xyz.cssxsh.baidu.disk

import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json
import xyz.cssxsh.baidu.*

/**
 * [document](https://pan.baidu.com/union/document/basic#%E8%8E%B7%E5%8F%96%E7%94%A8%E6%88%B7%E4%BF%A1%E6%81%AF)
 */
suspend fun NetDiskClient.getUserInfo(): NetDiskUserInfo = useHttpClient { client ->
    client.get(PAN_NAS) {
        parameter("access_token", accessToken)
        parameter("method", "uinfo")
    }
}

/**
 * [document](https://pan.baidu.com/union/document/basic#%E8%8E%B7%E5%8F%96%E6%96%87%E4%BB%B6%E5%88%97%E8%A1%A8)
 */
suspend fun NetDiskClient.listFile(
    dir: String = "",
    order: OrderType? = null,
    desc: Boolean? = null,
    start: Int? = null,
    limit: Int? = null,
    web: Boolean? = null,
    folder: Boolean? = null,
    showEmpty: Int? = null,
): NetDiskList<NetDiskFileOrDir> = useHttpClient { client ->
    client.get(PAN_FILE) {
        parameter("access_token", accessToken)
        parameter("method", "list")
        parameter("dir", withAppDataFolder(dir))
        parameter("order", order?.value)
        parameter("desc", desc?.toInt())
        parameter("start", start)
        parameter("limit", limit)
        parameter("web", if (web == true) "web" else null)
        parameter("folder", folder)
        parameter("showempty", showEmpty)
    }
}

/**
 * XXX
 */
suspend fun NetDiskClient.listAllFile(
    path: String = "",
    order: OrderType? = null,
    desc: Boolean? = null,
    start: Int? = null,
    limit: Int? = null,
    recursion: Boolean? = null,
    ctime: Long? = null,
    mtime: Long? = null,
    web: Boolean? = null,
): NetDiskFileList = useHttpClient { client ->
    client.get(PAN_MULTIMEDIA) {
        parameter("access_token", accessToken)
        parameter("method", "listall")
        parameter("path", withAppDataFolder(path))
        parameter("order", order?.value)
        parameter("desc", desc?.toInt())
        parameter("start", start)
        parameter("limit", limit)
        parameter("recursion", recursion?.toInt())
        parameter("ctime", ctime)
        parameter("mtime", mtime)
        parameter("web", web?.toInt())
    }
}

/**
 * [document](https://pan.baidu.com/union/document/basic#%E8%8E%B7%E5%8F%96%E6%96%87%E6%A1%A3%E5%88%97%E8%A1%A8)
 */
suspend fun NetDiskClient.listDoc(
    page: Int? = null,
    num: Int? = null,
    order: OrderType? = null,
    desc: Boolean? = null,
    path: String = "",
    recursion: Boolean? = null,
    web: Boolean? = null,
): NetDiskList<NetDiskDoc> = useHttpClient { client ->
    client.get(PAN_FILE) {
        parameter("access_token", accessToken)
        parameter("method", "doclist")
        parameter("page", page)
        parameter("num", num)
        parameter("order", order?.value)
        parameter("desc", desc?.toInt())
        parameter("parent_path", withAppDataFolder(path))
        parameter("recursion", recursion?.toInt())
        parameter("web", if (web == true) "web" else null)
    }
}

/**
 * [document](https://pan.baidu.com/union/document/basic#%E8%8E%B7%E5%8F%96%E8%A7%86%E9%A2%91%E5%88%97%E8%A1%A8)
 */
suspend fun NetDiskClient.listVideo(
    page: Int? = null,
    num: Int? = null,
    order: OrderType? = null,
    desc: Boolean? = null,
    path: String = "",
    recursion: Boolean? = null,
): NetDiskList<NetDiskVideo> = useHttpClient { client ->
    client.get(PAN_FILE) {
        parameter("access_token", accessToken)
        parameter("method", "videolist")
        parameter("page", page)
        parameter("num", num)
        parameter("order", order?.value)
        parameter("desc", desc?.toInt())
        parameter("parent_path", withAppDataFolder(path))
        parameter("recursion", recursion?.toInt())
    }
}

/**
 * [document](https://pan.baidu.com/union/document/basic#%E8%8E%B7%E5%8F%96%E8%A7%86%E9%A2%91%E5%88%97%E8%A1%A8)
 */
suspend fun NetDiskClient.listBt(
    page: Int? = null,
    num: Int? = null,
    order: OrderType? = null,
    desc: Boolean? = null,
    path: String = "",
    recursion: Boolean? = null,
): NetDiskList<NetDiskBt> = useHttpClient { client ->
    client.get(PAN_FILE) {
        parameter("access_token", accessToken)
        parameter("method", "btlist")
        parameter("page", page)
        parameter("num", num)
        parameter("order", order?.value)
        parameter("desc", desc?.toInt())
        parameter("parent_path", withAppDataFolder(path))
        parameter("recursion", recursion?.toInt())
    }
}

/**
 * [document](https://pan.baidu.com/union/document/basic#%E8%8E%B7%E5%8F%96%E5%88%86%E7%B1%BB%E5%88%97%E8%A1%A8%E6%96%87%E4%BB%B6)
 */
suspend fun NetDiskClient.listCategoryFile(
    categories: List<CategoryType>,
    path: String = "",
    recursion: Boolean? = null,
    ext: List<String> = emptyList(),
    start: Int? = null,
    limit: Int? = null,
    order: OrderType? = null,
    desc: Boolean? = null,
): NetDiskFileList = useHttpClient { client ->
    client.get(PAN_MULTIMEDIA) {
        parameter("access_token", accessToken)
        parameter("method", "categorylist")
        parameter("category", categories.joinToString(",") { it.ordinal.toString() })
        parameter("parent_path", withAppDataFolder(path))
        parameter("recursion", recursion?.toInt())
        parameter("ext", ext.joinToString(","))
        parameter("order", order)
        parameter("desc", desc?.toInt())
        parameter("start", start)
        parameter("limit", limit)
    }
}

/**
 * [document](https://pan.baidu.com/union/document/basic#%E6%90%9C%E7%B4%A2%E6%96%87%E4%BB%B6)
 */
suspend fun NetDiskClient.searchFile(
    key: String,
    dir: String? = null,
    recursion: Boolean? = null,
    page: Int? = null,
    num: Int? = null,
    web: Boolean? = null,
): NetDiskList<NetDiskFileOrDir> = useHttpClient { client ->
    client.get(PAN_FILE) {
        parameter("access_token", accessToken)
        parameter("method", "search")
        parameter("key", key)
        parameter("dir", dir)
        parameter("recursion", recursion?.toInt())
        parameter("page", page)
        parameter("num", num)
        parameter("web", web?.not())
    }
}

/**
 * [document](https://pan.baidu.com/union/document/basic#%E6%9F%A5%E8%AF%A2%E6%96%87%E4%BB%B6%E4%BF%A1%E6%81%AF)
 */
suspend fun NetDiskClient.listFileById(
    ids: List<Long>,
    path: String = "",
    thumb: Boolean? = null,
    link: Boolean? = null,
    extra: Boolean? = null,
): NetDiskDetailList = useHttpClient { client ->
    client.get(PAN_MULTIMEDIA) {
        parameter("access_token", accessToken)
        parameter("method", "filemetas")
        parameter("fsids", ids)
        parameter("path", withAppDataFolder(path))
        parameter("thumb", thumb?.toInt())
        parameter("dlink", link?.toInt())
        parameter("thumb", extra?.toInt())
    }
}

/**
 * [document]([document](https://pan.baidu.com/union/document/basic#%E6%9F%A5%E8%AF%A2%E6%96%87%E4%BB%B6%E4%BF%A1%E6%81%AF))
 */
suspend fun NetDiskClient.operaFile(// TODO DEBUG
    async: AsyncType = AsyncType.AUTO,
    opera: FileOpera,
    type: FileOnDupType? = null,
): NetDiskOpera = useHttpClient { client ->
    client.get(PAN_FILE) {
        parameter("access_token", accessToken)
        parameter("method", "filemanager")
        parameter("opera", opera.name)
        body = FormDataContent(Parameters.build {
            appendParameter("async", async.ordinal)
            appendParameter("filelist", Json.encodeToString(FileOpera.serializer(), opera))
            appendParameter("ondup", type)
        })
    }
}

/**
 * [document](https://pan.baidu.com/union/document/basic#%E9%A2%84%E4%B8%8A%E4%BC%A0)
 */
suspend fun NetDiskClient.preCreate(
    path: String,
    size: Long,
    isDir: Boolean,
    blocks: List<String>,
    content: String? = null,
    slice: String? = null,
    createdTime: Long? = null,
    modifiedTime: Long? = null,
    rename: RenameType? = null,
    uploadId: String? = null,
): NetDiskPreCreate = useHttpClient { client ->
    client.post(PAN_FILE) {
        parameter("access_token", accessToken)
        parameter("method", "precreate")
        body = FormDataContent(Parameters.build {
            appendParameter("path", withAppDataFolder(path))
            appendParameter("size", size)
            appendParameter("isdir", isDir.toInt())
            appendParameter("autoinit", "1")
            appendParameter("block_list", Json.encodeToString(ListSerializer(String.serializer()), blocks))
            appendParameter("content-md5", content)
            appendParameter("slice-md5", slice)
            appendParameter("local_ctime", createdTime)
            appendParameter("local_mtime", modifiedTime)
            appendParameter("rtype", rename?.ordinal)
            appendParameter("uploadid", uploadId)
        })
    }
}

/**
 * [document](https://pan.baidu.com/union/document/basic#%E5%88%9B%E5%BB%BA%E6%96%87%E4%BB%B6)
 */
suspend fun NetDiskClient.createFile(
    path: String,
    size: Long,
    isDir: Boolean,
    uploadId: String? = null,
    rename: RenameType? = null,
    blocks: List<String>? = null,
    createdTime: Long? = null,
    modifiedTime: Long? = null,
    isRevision: Boolean? = null,
    exifInfo: String? = null,
    zipQuality: Int? = null,
    zipSign: Int? = null,
): NetDiskCreateFile = useHttpClient { client ->
    client.post(PAN_FILE) {
        parameter("access_token", accessToken)
        parameter("method", "create")
        body = FormDataContent(Parameters.build {
            appendParameter("path", withAppDataFolder(path))
            appendParameter("size", size)
            appendParameter("isdir", isDir.toInt())
            appendParameter("rtype", rename?.ordinal)
            appendParameter("uploadid", uploadId)
            appendParameter("autoinit", "1")
            appendParameter("block_list", blocks?.let { Json.encodeToString(ListSerializer(String.serializer()), it) })
            appendParameter("is_revision", isRevision?.toInt())
            appendParameter("exif_info", exifInfo)
            appendParameter("local_ctime", createdTime)
            appendParameter("local_mtime", modifiedTime)
            appendParameter("zip_quality", zipQuality)
            appendParameter("zip_sign", zipSign)
        })
    }
}