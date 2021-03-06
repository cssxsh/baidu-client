package xyz.cssxsh.baidu.disk

import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import io.ktor.utils.io.core.*
import xyz.cssxsh.baidu.*

/**
 * [document](https://pan.baidu.com/union/document/basic#%E5%88%86%E7%89%87%E4%B8%8A%E4%BC%A0)
 */
suspend fun NetDiskClient.superFile(
    path: String,
    uploadId: String,
    index: Int,
    data: ByteArray,
    size: Int,
): NetDiskSuperFile = useHttpClient { client ->
    client.post(PCS_SUPER_FILE) {
        parameter("access_token", accessToken)
        parameter("method", "upload")
        parameter("type", "tmpfile")
        parameter("path", withAppDataFolder(path))
        parameter("uploadid", uploadId)
        parameter("partseq", index)

        body = MultiPartFormDataContent(formData {
            append(key = "file", filename = "blob", size = size.toLong()) {
                writeFully(src = data, offset = 0, length = size)
            }
        })
    }
}

/**
 * XXX
 */
suspend fun NetDiskClient.uploadSingleFile(
    path: String,
    bytes: ByteArray,
    size: Int = bytes.size,
): NetDiskSingleFile = useHttpClient { client ->
    client.post(PCS_FILE) {
        parameter("access_token", accessToken)
        parameter("method", "upload")
        parameter("path", withAppDataFolder(path))

        body = MultiPartFormDataContent(formData {
            append(key = "file", filename = "blob", size = size.toLong()) {
                writeFully(src = bytes, offset = 0, length = size)
            }
        })
    }
}

/**
 * XXX
 */
suspend fun NetDiskClient.getMetaInfo(
    path: String
): NetDiskMetaList = useHttpClient { client ->
    client.post(PCS_FILE) {
        parameter("access_token", accessToken)
        parameter("method", "meta")
        parameter("path", withAppDataFolder(path))
    }
}

/**
 * XXX
 */
fun NetDiskClient.downloadFileUrl(
    path: String
): Url = URLBuilder(PCS_FILE).apply {
    parameters.apply {
        appendParameter("access_token", accessToken)
        appendParameter("method", "download")
        appendParameter("path", path)
    }
}.build()