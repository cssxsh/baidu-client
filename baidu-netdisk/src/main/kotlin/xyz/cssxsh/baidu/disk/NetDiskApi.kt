package xyz.cssxsh.baidu.disk

import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import kotlinx.serialization.builtins.*
import kotlinx.serialization.json.*
import xyz.cssxsh.baidu.*

/**
 * [document](https://pan.baidu.com/union/document/basic#%E8%8E%B7%E5%8F%96%E7%BD%91%E7%9B%98%E5%AE%B9%E9%87%8F%E4%BF%A1%E6%81%AF)
 */
suspend fun NetDiskClient.getQuotaInfo(
    checkFree: Boolean? = null,
    checkExpire: Boolean? = null,
): NetDiskQuotaInfo = useHttpClient { client ->
    client.get(API_QUOTA) {
        parameter("access_token", accessToken)
        parameter("checkfree", checkFree?.toInt())
        parameter("checkexpire", checkExpire?.toInt())
    }
}

/**
 * [document](https://pan.baidu.com/union/document/basic#%E8%8E%B7%E5%8F%96%E5%88%86%E7%B1%BB%E6%96%87%E4%BB%B6%E6%80%BB%E4%B8%AA%E6%95%B0)
 */
suspend fun NetDiskClient.getCategoryInfo(
    categories: List<CategoryType>? = null,
    path: String = "",
    recursion: Boolean? = null,
): NetDiskCategoryList = useHttpClient { client ->
    client.get(API_CATEGORY_INFO) {
        parameter("access_token", accessToken)
        parameter("category", categories?.joinToString(",") { it.ordinal.toString() })
        parameter("parent_path", withAppDataFolder(path))
        parameter("recursion", recursion?.toInt())
    }
}

/**
 * XXX
 */
suspend fun NetDiskClient.getListInfo(
    dir: String = "",
    order: OrderType? = null,
    desc: Boolean? = null,
    start: Int? = null,
    limit: Int? = null,
    web: Boolean? = null,
    folder: Boolean? = null,
    showEmpty: Int? = null,
): NetDiskList<NetDiskFileOrDir> = useHttpClient { client ->
    client.get(API_LIST) {
        parameter("access_token", accessToken)
        parameter("method", "list")
        parameter("dir", withAppDataFolder(dir))
        parameter("order", order?.value)
        parameter("desc", desc?.toInt())
        parameter("start", start)
        parameter("limit", limit)
        parameter("web", web?.toInt())
        parameter("folder", folder)
        parameter("showempty", showEmpty)
    }
}

/**
 * XXX
 */
suspend fun NetDiskClient.rapidUpload(
    content: String,
    slice: String,
    length: Long,
    path: String,
    rename: RenameType? = null
): NetDiskRapidInfo = useHttpClient { client ->
    client.post(API_RAPID_UPLOAD) {
        parameter("access_token", accessToken)
        body = FormDataContent(Parameters.build {
            appendParameter("rtype", rename?.ordinal)
            appendParameter("path", withAppDataFolder(path))
            appendParameter("content-md5", content)
            appendParameter("slice-md5", slice)
            appendParameter("content-length", length)
        })
    }
}

/**
 * XXX
 */
suspend fun NetDiskClient.createFileWeb(
    path: String,
    size: Long,
    isDir: Boolean,
    uploadId: String?,
    rename: RenameType? = null,
    blocks: List<String>? = null,
    createdTime: Long? = null,
    modifiedTime: Long? = null,
    isRevision: Boolean? = null,
    exifInfo: String? = null,
    zipQuality: Int? = null,
    zipSign: Int? = null,
): NetDiskCreateFile = useHttpClient { client ->
    client.post(API_CREATE) {
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