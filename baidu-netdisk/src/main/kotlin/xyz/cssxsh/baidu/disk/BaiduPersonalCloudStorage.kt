package xyz.cssxsh.baidu.disk

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.json.*
import xyz.cssxsh.baidu.api.*
import xyz.cssxsh.baidu.disk.data.*

/**
 * Personal Cloud Storage
 * [overview](https://openapi.baidu.com/wiki/index.php?title=docs/pcs/overview)
 * [文件API列表](https://openapi.baidu.com/wiki/index.php?title=docs/pcs/rest/file_data_apis_list)
 */
public class BaiduPersonalCloudStorage internal constructor(public val client: NetDiskClient) {
    public companion object {
        internal const val QUOTA: String = "https://pcs.baidu.com/rest/2.0/pcs/quota"
        internal const val FILE: String = "https://pcs.baidu.com/rest/2.0/pcs/file"
        internal const val SUPER_FILE: String = "https://pcs.baidu.com/rest/2.0/pcs/superfile2"
    }

    internal var upload: PCSServer = PCSServer(
        clientIp = "0.0.0.0",
        expire = 0,
        host = "pcs.baidu.com",
        server = listOf("pcs.baidu.com", "c.pcs.baidu.com", "d.pcs.baidu.com"),
        requestId = 0
    )

    public suspend fun quota(): PCSQuotaInfo {
        return client.useHttpClient { http ->
            http.get(QUOTA) {
                parameter("method", "info")
                parameter("access_token", client.accessToken())
            }.body()
        }
    }

    public suspend fun upload(path: String, ondup: OnDupType, size: Long, input: BodyBuilder): PCSUploadFile {
        return client.useHttpClient { http ->
            http.submitFormWithBinaryData(FILE, formData {
                append(key = "file".quote(), filename = "blob".quote(), size = size, bodyBuilder = input)
            }) {
                url {
                    host = upload.server.randomOrNull() ?: upload.host
                }
                parameter("method", "upload")
                parameter("access_token", client.accessToken())
                parameter("path", client.appDataFolder(path = path))
                parameter("ondup", ondup)
            }.body()
        }
    }

    public suspend fun rapid(upload: RapidUploadInfo, ondup: OnDupType): PCSUploadFile {
        return client.useHttpClient { http ->
            http.post(FILE) {
                parameter("method", "rapidupload")
                parameter("access_token", client.accessToken())
                parameter("path", client.appDataFolder(path = upload.path))
                parameter("content-length", upload.length)
                parameter("content-md5", upload.content)
                parameter("slice-md5", upload.slice)
                // parameter("content-crc32", "...")
                parameter("ondup", ondup)
            }.body()
        }
    }

    public suspend fun temp(path: String, size: Int, input: BodyBuilder): PCSTempInfo {
        return client.useHttpClient { http ->
            http.submitFormWithBinaryData(FILE, formData {
                append(key = "file".quote(), filename = "blob".quote(), size = size.toLong(), bodyBuilder = input)
            }) {
                url {
                    host = upload.server.randomOrNull() ?: upload.host
                }
                parameter("method", "upload")
                parameter("type", "tmpfile")
                parameter("access_token", client.accessToken())
                parameter("path", client.appDataFolder(path = path))
            }.body()
        }
    }

    public suspend fun temp(path: String, id: String, index: Int, size: Int, input: BodyBuilder): PCSTempInfo {
        return client.useHttpClient { http ->
            http.submitFormWithBinaryData(SUPER_FILE, formData {
                append(key = "file".quote(), filename = "blob".quote(), size = size.toLong(), bodyBuilder = input)
            }) {
                url {
                    host = upload.server.randomOrNull() ?: upload.host
                }
                parameter("method", "upload")
                parameter("type", "tmpfile")
                parameter("access_token", client.accessToken())
                parameter("path", client.appDataFolder(path = path))
                parameter("uploadid", id)
                parameter("partseq", index)
                parameter("web", "1")
            }.body()
        }
    }

    public suspend fun create(path: String, blocks: List<String>, ondup: OnDupType): PCSUploadFile {
        return client.useHttpClient { http ->
            http.submitFormWithBinaryData(FILE, formData {
                append(
                    key = "param".quote(),
                    value = buildJsonObject {
                        putJsonArray("block_list") {
                            blocks.forEach { md5 ->
                                add(md5)
                            }
                        }
                    }.toString()
                )
            }) {
                parameter("method", "createsuperfile")
                parameter("access_token", client.accessToken())
                parameter("path", client.appDataFolder(path = path))
                parameter("ondup", ondup)
            }.body()
        }
    }

    public suspend fun download(path: String, block: HttpRequestBuilder.() -> Unit): HttpStatement {
        return client.useHttpClient { http ->
            http.prepareGet(FILE) {
                url {
                    host = upload.server.randomOrNull() ?: upload.host
                }
                parameter("method", "download")
                parameter("access_token", client.accessToken())
                parameter("path", client.appDataFolder(path = path))

                block()
            }
        }
    }

    public suspend fun load(dlink: String, block: HttpRequestBuilder.() -> Unit): HttpStatement {
        return client.useHttpClient { http ->
            http.prepareGet(dlink) {
                parameter("access_token", client.accessToken())

                block()
            }
        }
    }

    public suspend fun mkdir(path: String): PCSUploadFile {
        return client.useHttpClient { http ->
            http.get(FILE) {
                parameter("method", "mkdir")
                parameter("access_token", client.accessToken())
                parameter("path", client.appDataFolder(path = path))
            }.body()
        }
    }

    public suspend fun meta(path: String): PCSFileList<PCSFile> {
        return client.useHttpClient { http ->
            http.get(FILE) {
                parameter("method", "meta")
                parameter("access_token", client.accessToken())
                parameter("path", client.appDataFolder(path = path))
            }.body()
        }
    }

    public suspend fun meta(vararg list: String): PCSFileList<PCSFile> {
        return client.useHttpClient { http ->
            http.submitFormWithBinaryData(FILE, formData {
                append(
                    key = "param".quote(),
                    value = buildJsonObject {
                        putJsonArray("list") {
                            list.forEach { path ->
                                addJsonObject {
                                    put("path", client.appDataFolder(path = path))
                                }
                            }
                        }
                    }.toString()
                )
            }) {
                parameter("method", "meta")
                parameter("access_token", client.accessToken())
            }.body()
        }
    }

    public suspend fun list(path: String, order: OrderType, desc: Boolean, limit: IntRange?): PCSFileList<PCSFile> {
        return client.useHttpClient { http ->
            http.get(FILE) {
                parameter("method", "list")
                parameter("access_token", client.accessToken())
                parameter("path", client.appDataFolder(path = path))
                parameter("by", order)
                parameter("order", if (desc) "desc" else "asc")
                parameter("limit", limit?.run { "${first}-${last + 1}" })
            }.body()
        }
    }

    public suspend fun move(from: String, to: String): PCSExtra<PCSFileChange> {
        return client.useHttpClient { http ->
            http.post(FILE) {
                parameter("method", "move")
                parameter("access_token", client.accessToken())
                parameter("from", client.appDataFolder(path = from))
                parameter("to", client.appDataFolder(path = to))
            }.body()
        }
    }

    public suspend fun move(vararg list: Pair<String, String>): PCSExtra<PCSFileChange> {
        return client.useHttpClient { http ->
            http.submitFormWithBinaryData(FILE, formData {
                append(
                    key = "param".quote(),
                    value = buildJsonObject {
                        putJsonArray("list") {
                            list.forEach { (from, to) ->
                                addJsonObject {
                                    put("from", client.appDataFolder(path = from))
                                    put("to", client.appDataFolder(path = to))
                                }
                            }
                        }
                    }.toString()
                )
            }) {
                parameter("method", "move")
                parameter("access_token", client.accessToken())
            }.body()
        }
    }

    public suspend fun copy(from: String, to: String): PCSExtra<PCSFileChange> {
        return client.useHttpClient { http ->
            http.post(FILE) {
                parameter("method", "copy")
                parameter("access_token", client.accessToken())
                parameter("from", client.appDataFolder(path = from))
                parameter("to", client.appDataFolder(path = to))
            }.body()
        }
    }

    public suspend fun copy(vararg list: Pair<String, String>): PCSExtra<PCSFileChange> {
        return client.useHttpClient { http ->
            http.submitFormWithBinaryData(FILE, formData {
                append(
                    key = "param".quote(),
                    value = buildJsonObject {
                        putJsonArray("list") {
                            list.forEach { (from, to) ->
                                addJsonObject {
                                    put("from", client.appDataFolder(path = from))
                                    put("to", client.appDataFolder(path = to))
                                }
                            }
                        }
                    }.toString()
                )
            }) {
                parameter("method", "copy")
                parameter("access_token", client.accessToken())
            }.body()
        }
    }

    public suspend fun delete(path: String): PCSError {
        return client.useHttpClient { http ->
            http.post(FILE) {
                parameter("method", "delete")
                parameter("access_token", client.accessToken())
                parameter("path", client.appDataFolder(path = path))
            }.body()
        }
    }

    public suspend fun delete(vararg list: String): PCSError {
        return client.useHttpClient { http ->
            http.submitFormWithBinaryData(FILE, formData {
                append(
                    key = "param".quote(),
                    value = buildJsonObject {
                        putJsonArray("list") {
                            list.forEach { path ->
                                addJsonObject {
                                    put("path", client.appDataFolder(path = path))
                                }
                            }
                        }
                    }.toString()
                )
            }) {
                parameter("method", "delete")
                parameter("access_token", client.accessToken())
            }.body()
        }
    }

    public suspend fun search(path: String, word: String, recursion: Boolean): PCSFileList<PCSSearchFile> {
        return client.useHttpClient { http ->
            http.get(FILE) {
                parameter("method", "search")
                parameter("access_token", client.accessToken())
                parameter("path", path)
                parameter("wd", word)
                parameter("re", recursion.toInt())
            }.body()
        }
    }

    public suspend fun recycle(start: Int, limit: Int): PCSFileList<PCSSearchFile> {
        return client.useHttpClient { http ->
            http.get(FILE) {
                parameter("method", "listrecycle")
                parameter("access_token", client.accessToken())
                parameter("start", start)
                parameter("limit", limit)
            }.body()
        }
    }

    public suspend fun restore(id: Long): PCSExtra<PCSFileRestore> {
        return client.useHttpClient { http ->
            http.post(FILE) {
                parameter("method", "restore")
                parameter("access_token", client.accessToken())
                parameter("fs_id", id)
            }.body()
        }
    }

    public suspend fun restore(vararg list: Long): PCSExtra<PCSFileRestore> {
        return client.useHttpClient { http ->
            http.submitFormWithBinaryData(FILE, formData {
                append(
                    key = "param".quote(),
                    value = buildJsonObject {
                        putJsonArray("list") {
                            list.forEach { id ->
                                addJsonObject {
                                    put("fs_id", id)
                                }
                            }
                        }
                    }.toString()
                )
            }) {
                parameter("method", "restore")
                parameter("access_token", client.accessToken())
            }.body()
        }
    }

    public suspend fun shred(): PCSError {
        return client.useHttpClient { http ->
            http.post(FILE) {
                parameter("method", "delete")
                parameter("access_token", client.accessToken())
                parameter("type", "recycle")
            }.body()
        }
    }

    public suspend fun host(): PCSServer {
        return client.useHttpClient { http ->
            http.post(FILE) {
                parameter("method", "locateupload")
                parameter("access_token", client.accessToken())
            }.body()
        }
    }
}