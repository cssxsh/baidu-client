package xyz.cssxsh.baidu.disk

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import io.ktor.utils.io.core.*
import xyz.cssxsh.baidu.disk.data.*

/**
 * [document](https://pan.baidu.com/union/document/basic#%E5%88%86%E7%89%87%E4%B8%8A%E4%BC%A0)
 */
public suspend fun NetDiskClient.superFile(
    path: String,
    uploadId: String,
    index: Int,
    data: ByteArray,
    size: Int,
): NetDiskSuperFile = useHttpClient { client ->
    client.submitFormWithBinaryData(formData {
        append(key = "file", filename = "blob", size = size.toLong()) {
            writeFully(src = data, offset = 0, length = size)
        }
    }) {
        url(PCS_SUPER_FILE)
        parameter("access_token", accessToken())
        parameter("method", "upload")
        parameter("type", "tmpfile")
        parameter("path", appDataFolder(path))
        parameter("uploadid", uploadId)
        parameter("partseq", index)
    }.body()
}

/**
 * XXX
 */
public suspend fun NetDiskClient.uploadSingleFile(
    path: String,
    bytes: ByteArray,
    size: Int = bytes.size,
): NetDiskSingleFile = useHttpClient { client ->
    client.submitFormWithBinaryData(formData {
        append(key = "file", filename = "blob", size = size.toLong()) {
            writeFully(src = bytes, offset = 0, length = size)
        }
    }) {
        url(PCS_FILE)
        parameter("access_token", accessToken())
        parameter("method", "upload")
        parameter("path", appDataFolder(path))
    }.body()
}

/**
 * XXX
 */
public suspend fun NetDiskClient.getMetaInfo(
    path: String
): NetDiskMetaList = useHttpClient { client ->
    client.post(PCS_FILE) {
        parameter("access_token", accessToken())
        parameter("method", "meta")
        parameter("path", appDataFolder(path))
    }.body()
}

/**
 * XXX
 */
public suspend fun NetDiskClient.downloadFileUrl(
    path: String
): Url = URLBuilder(PCS_FILE).apply {
    parameters.apply {
        appendParameter("access_token", accessToken())
        appendParameter("method", "download")
        appendParameter("path", appDataFolder(path))
    }
}.build()