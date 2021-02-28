package xyz.cssxsh.baidu.disk

import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import io.ktor.utils.io.core.*
import xyz.cssxsh.baidu.*

/**
 * [document](https://pan.baidu.com/union/document/basic#%E8%8E%B7%E5%8F%96%E7%94%A8%E6%88%B7%E4%BF%A1%E6%81%AF)
 */
suspend fun AbstractNetDiskClient.getUserInfo(): NetDiskUserInfo = useHttpClient { client ->
    client.get(NetDiskApi.PAN_NAS) {
        parameter("access_token", accessToken)
        parameter("method", "uinfo")
    }
}

/**
 * [document](https://pan.baidu.com/union/document/basic#%E8%8E%B7%E5%8F%96%E7%BD%91%E7%9B%98%E5%AE%B9%E9%87%8F%E4%BF%A1%E6%81%AF)
 */
suspend fun AbstractNetDiskClient.getQuotaInfo(
    checkFree: Boolean? = null,
    checkExpire: Boolean? = null,
): NetDiskQuotaInfo = useHttpClient { client ->
    client.get(NetDiskApi.API_QUOTA) {
        parameter("access_token", accessToken)
        parameter("checkfree", checkFree?.toInt())
        parameter("checkexpire", checkExpire?.toInt())
    }
}

/**
 * [document](https://pan.baidu.com/union/document/basic#%E8%8E%B7%E5%8F%96%E6%96%87%E4%BB%B6%E5%88%97%E8%A1%A8)
 */
suspend fun AbstractNetDiskClient.listFile(
    dir: String? = null,
    order: OrderType? = null,
    desc: Boolean? = null,
    start: Int? = null,
    limit: Int? = null,
    web: Boolean? = null,
    folder: Boolean? = null,
    showEmpty: Int? = null,
): NetDiskMultiList = useHttpClient { client ->
    client.get(NetDiskApi.PAN_FILE) {
        parameter("access_token", accessToken)
        parameter("method", "list")
        parameter("dir", dir)
        parameter("order", order?.value)
        parameter("desc", desc?.toInt())
        parameter("start", start)
        parameter("limit", limit)
        parameter("web", if (web == true) "web" else null)
        parameter("folder", folder)
        parameter("showempty", showEmpty)
    }
}

suspend fun AbstractNetDiskClient.listAllFile(
    path: String? = null,
    order: OrderType? = null,
    desc: Boolean? = null,
    start: Int? = null,
    limit: Int? = null,
    recursion: Boolean? = null,
    ctime: Long? = null,
    mtime: Long? = null,
    web: Boolean? = null,
): NetDiskFileList = useHttpClient { client ->
    client.get(NetDiskApi.PAN_MULTIMEDIA) {
        parameter("access_token", accessToken)
        parameter("method", "listall")
        parameter("path", path)
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
suspend fun AbstractNetDiskClient.listDoc(
    page: Int? = null,
    num: Int? = null,
    order: OrderType? = null,
    desc: Boolean? = null,
    path: String? = null,
    recursion: Boolean? = null,
    web: Boolean? = null,
): NetDiskDocList = useHttpClient { client ->
    client.get(NetDiskApi.PAN_FILE) {
        parameter("access_token", accessToken)
        parameter("method", "doclist")
        parameter("page", page)
        parameter("num", num)
        parameter("order", order?.value)
        parameter("desc", desc?.toInt())
        parameter("parent_path", path)
        parameter("recursion", recursion?.toInt())
        parameter("web", if (web == true) "web" else null)
    }
}

/**
 * [document](https://pan.baidu.com/union/document/basic#%E8%8E%B7%E5%8F%96%E8%A7%86%E9%A2%91%E5%88%97%E8%A1%A8)
 */
suspend fun AbstractNetDiskClient.listVideo(
    page: Int? = null,
    num: Int? = null,
    order: OrderType? = null,
    desc: Boolean? = null,
    path: String? = null,
    recursion: Boolean? = null,
): NetDiskVideoList = useHttpClient { client ->
    client.get(NetDiskApi.PAN_FILE) {
        parameter("access_token", accessToken)
        parameter("method", "videolist")
        parameter("page", page)
        parameter("num", num)
        parameter("order", order?.value)
        parameter("desc", desc?.toInt())
        parameter("parent_path", path)
        parameter("recursion", recursion?.toInt())
    }
}

/**
 * [document](https://pan.baidu.com/union/document/basic#%E8%8E%B7%E5%8F%96%E8%A7%86%E9%A2%91%E5%88%97%E8%A1%A8)
 */
suspend fun AbstractNetDiskClient.listBt(
    page: Int? = null,
    num: Int? = null,
    order: OrderType? = null,
    desc: Boolean? = null,
    path: String? = null,
    recursion: Boolean? = null,
): NetDiskBtList = useHttpClient { client ->
    client.get(NetDiskApi.PAN_FILE) {
        parameter("access_token", accessToken)
        parameter("method", "btlist")
        parameter("page", page)
        parameter("num", num)
        parameter("order", order?.value)
        parameter("desc", desc?.toInt())
        parameter("parent_path", path)
        parameter("recursion", recursion?.toInt())
    }
}

/**
 * [document](https://pan.baidu.com/union/document/basic#%E8%8E%B7%E5%8F%96%E5%88%86%E7%B1%BB%E6%96%87%E4%BB%B6%E6%80%BB%E4%B8%AA%E6%95%B0)
 */
suspend fun AbstractNetDiskClient.getCategoryInfo(
    category: CategoryType? = null,
    path: String? = null,
    recursion: Boolean? = null,
): NetDiskCategoryList = useHttpClient { client ->
    client.get(NetDiskApi.PAN_FILE) {
        parameter("access_token", accessToken)
        parameter("category", category?.ordinal)
        parameter("parent_path", path)
        parameter("recursion", recursion?.toInt())
    }
}

/**
 * [document](https://pan.baidu.com/union/document/basic#%E8%8E%B7%E5%8F%96%E5%88%86%E7%B1%BB%E5%88%97%E8%A1%A8%E6%96%87%E4%BB%B6)
 */
suspend fun AbstractNetDiskClient.listCategoryFile(
    categories: List<CategoryType> = emptyList(),
    path: String? = null,
    recursion: Boolean? = null,
    ext: List<String> = emptyList(),
    start: Int? = null,
    limit: Int? = null,
    order: OrderType? = null,
    desc: Boolean? = null,
): NetDiskFileList = useHttpClient { client ->
    client.get(NetDiskApi.PAN_MULTIMEDIA) {
        parameter("access_token", accessToken)
        parameter("method", "categorylist")
        parameter("category", categories.joinToString(",") { it.ordinal.toString() })
        parameter("parent_path", path)
        parameter("recursion", recursion?.toInt())
        parameter("ext", ext.joinToString(","))
        parameter("order", order?.value)
        parameter("desc", desc?.toInt())
        parameter("start", start)
        parameter("limit", limit)
    }
}

/**
 * [document](https://pan.baidu.com/union/document/basic#%E6%90%9C%E7%B4%A2%E6%96%87%E4%BB%B6)
 */
suspend fun AbstractNetDiskClient.searchFile(
    key: String,
    dir: String? = null,
    recursion: Boolean? = null,
    page: Int? = null,
    num: Int? = null,
    web: Boolean? = null,
): NetDiskMultiList = useHttpClient { client ->
    client.get(NetDiskApi.PAN_FILE) {
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
suspend fun AbstractNetDiskClient.listFileById(
    ids: List<Long>,
    path: String? = null,
    thumb: Boolean? = null,
    link: Boolean? = null,
    extra: Boolean? = null,
): NetDiskDetailList = useHttpClient { client ->
    client.get(NetDiskApi.PAN_MULTIMEDIA) {
        parameter("access_token", accessToken)
        parameter("method", "filemetas")
        parameter("fsids", ids)
        parameter("path", path)
        parameter("thumb", thumb?.toInt())
        parameter("dlink", link?.toInt())
        parameter("thumb", extra?.toInt())
    }
}

/**
 * [document](https://pan.baidu.com/union/document/basic#%E9%A2%84%E4%B8%8A%E4%BC%A0)
 */
suspend fun AbstractNetDiskClient.preCreate(
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
    client.post(NetDiskApi.PAN_FILE) {
        parameter("access_token", accessToken)
        parameter("method", "precreate")
        body = FormDataContent(Parameters.build {
            appendParameter("path", path)
            appendParameter("size", size)
            appendParameter("isdir", isDir.toInt())
            appendParameter("autoinit", "1")
            appendParameter("block_list", blocks.joinToString(separator = "\",\"", prefix = "[\"", postfix = "\"]"))
            appendParameter("content-md5", content)
            appendParameter("slice-md5", slice)
            appendParameter("local_ctime", createdTime)
            appendParameter("local_mtime", modifiedTime)
            appendParameter("rtype", rename?.value)
            appendParameter("uploadid", uploadId)
        })
    }
}

/**
 * [document](https://pan.baidu.com/union/document/basic#%E5%88%86%E7%89%87%E4%B8%8A%E4%BC%A0)
 */
suspend fun AbstractNetDiskClient.superFile(
    path: String,
    uploadId: String,
    index: Int,
    data: ByteArray,
    size: Int,
): NetDiskSuperFile = useHttpClient { client ->
    client.post(NetDiskApi.PCS_SUPER_FILE) {
        parameter("access_token", accessToken)
        parameter("method", "upload")
        parameter("type", "tmpfile")
        parameter("path", path)
        parameter("uploadid", uploadId)
        parameter("partseq", index)

        body = MultiPartFormDataContent(formData {
            append(key = "file", filename = "blob", size = size.toLong()) {
                writeFully(src = data, offset = 0, length = size)
            }
        })
    }
}

suspend fun AbstractNetDiskClient.uploadSingleFile(
    path: String,
    data: ByteArray,
    size: Int = data.size,
): NetDiskSuperFile = useHttpClient { client ->
    client.post(NetDiskApi.PCS_FILE) {
        parameter("access_token", accessToken)
        parameter("app_id", appId)
        parameter("method", "upload")
        parameter("path", path)

        body = MultiPartFormDataContent(formData {
            append(key = "file", filename = "blob", size = size.toLong()) {
                writeFully(src = data, offset = 0, length = size)
            }
        })
    }
}

/**
 * [document](https://pan.baidu.com/union/document/basic#%E5%88%9B%E5%BB%BA%E6%96%87%E4%BB%B6)
 */
suspend fun AbstractNetDiskClient.createFile(
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
    client.post(NetDiskApi.PAN_FILE) {
        parameter("access_token", accessToken)
        parameter("method", "create")
        body = FormDataContent(Parameters.build {
            appendParameter("path", path)
            appendParameter("size", size)
            appendParameter("isdir", isDir.toInt())
            appendParameter("rtype", rename?.value)
            appendParameter("uploadid", uploadId)
            appendParameter("autoinit", "1")
            appendParameter("block_list", blocks?.joinToString(separator = "\",\"", prefix = "[\"", postfix = "\"]"))
            appendParameter("is_revision", isRevision?.toInt())
            appendParameter("exif_info", exifInfo)
            appendParameter("local_ctime", createdTime)
            appendParameter("local_mtime", modifiedTime)
            appendParameter("zip_quality", zipQuality)
            appendParameter("zip_sign", zipSign)
        })
    }
}