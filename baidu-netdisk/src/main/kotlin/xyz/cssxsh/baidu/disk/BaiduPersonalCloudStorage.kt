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

    @PublishedApi
    internal var hosts: PCSServer = PCSServer(
        clientIp = "0.0.0.0",
        expire = 0,
        host = "pcs.baidu.com",
        server = listOf("pcs.baidu.com", "c.pcs.baidu.com", "d.pcs.baidu.com"),
        requestId = 0
    )

    /**
     * 查询容量
     */
    public suspend fun quota(): PCSQuotaInfo {
        return client.useHttpClient { http ->
            http.get(QUOTA) {
                parameter("method", "info")
                parameter("access_token", client.accessToken())
            }.body()
        }
    }

    /**
     * 上传文件
     */
    public suspend fun upload(path: String, ondup: OnDupType, size: Long, input: BodyBuilder): PCSUploadFile {
        return client.useHttpClient { http ->
            http.submitFormWithBinaryData(FILE, formData {
                append(key = "file".quote(), filename = "blob".quote(), size = size, bodyBuilder = input)
            }) {
                url {
                    host = hosts.server.randomOrNull() ?: hosts.host
                }
                parameter("method", "upload")
                parameter("access_token", client.accessToken())
                parameter("path", appDataFolder(path = path))
                parameter("ondup", ondup)
            }.body()
        }
    }

    /**
     * 秒传文件
     */
    public suspend fun rapid(upload: RapidUploadInfo, ondup: OnDupType): PCSUploadFile {
        return client.useHttpClient { http ->
            http.post(FILE) {
                parameter("method", "rapidupload")
                parameter("access_token", client.accessToken())
                parameter("path", appDataFolder(path = upload.path))
                parameter("content-length", upload.length)
                parameter("content-md5", upload.content.lowercase())
                parameter("slice-md5", upload.slice.lowercase())
                parameter("ondup", ondup)
            }.body()
        }
    }

    /**
     * 秒传文件
     */
    public suspend fun temp(path: String, size: Int, input: BodyBuilder): PCSTempInfo {
        return client.useHttpClient { http ->
            http.submitFormWithBinaryData(FILE, formData {
                append(key = "file".quote(), filename = "blob".quote(), size = size.toLong(), bodyBuilder = input)
            }) {
                url {
                    host = hosts.server.randomOrNull() ?: hosts.host
                }
                parameter("method", "upload")
                parameter("type", "tmpfile")
                parameter("access_token", client.accessToken())
                parameter("path", appDataFolder(path = path))
            }.body()
        }
    }

    /**
     * 创建一个文件分片
     * @param path 文件路径
     * @param id 任务ID
     * @param index 序号
     * @param size 大小
     * @param input 输入DSL
     */
    public suspend fun temp(path: String, id: String, index: Int, size: Int, input: BodyBuilder): PCSTempInfo {
        return client.useHttpClient { http ->
            http.submitFormWithBinaryData(SUPER_FILE, formData {
                append(key = "file".quote(), filename = "blob".quote(), size = size.toLong(), bodyBuilder = input)
            }) {
                url {
                    host = hosts.server.randomOrNull() ?: hosts.host
                }
                parameter("method", "upload")
                parameter("type", "tmpfile")
                parameter("access_token", client.accessToken())
                parameter("path", appDataFolder(path = path))
                parameter("uploadid", id)
                parameter("partseq", index)
                parameter("web", "1")
            }.body()
        }
    }

    /**
     * 创建一个文件
     * @param path 文件路径
     * @param blocks 文件块MD5
     * @param ondup 文件冲突策略
     */
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
                parameter("path", appDataFolder(path = path))
                parameter("ondup", ondup)
            }.body()
        }
    }

    /**
     * 创建一个下载任务
     * @param path 文件路径
     * @param block HttpRequest DSL
     */
    public suspend fun download(path: String, block: HttpRequestBuilder.() -> Unit): HttpStatement {
        return client.useHttpClient { http ->
            http.prepareGet(FILE) {
                url {
                    host = hosts.server.randomOrNull() ?: hosts.host
                }
                parameter("method", "download")
                parameter("access_token", client.accessToken())
                parameter("path", appDataFolder(path = path))

                block()
            }
        }
    }

    /**
     * 创建一个下载任务
     * @param dlink 下载连接
     * @param block HttpRequest DSL
     */
    public suspend fun load(dlink: String, block: HttpRequestBuilder.() -> Unit): HttpStatement {
        return client.useHttpClient { http ->
            http.prepareGet(dlink) {
                parameter("access_token", client.accessToken())

                block()
            }
        }
    }

    /**
     * 创建文件夹
     * @param path 文件路径
     */
    public suspend fun mkdir(path: String): PCSUploadFile {
        return client.useHttpClient { http ->
            http.get(FILE) {
                parameter("method", "mkdir")
                parameter("access_token", client.accessToken())
                parameter("path", appDataFolder(path = path))
            }.body()
        }
    }

    /**
     * 获取文件元数据
     * @param path 文件路径
     */
    public suspend fun meta(path: String): PCSFileList<PCSFile> {
        return client.useHttpClient { http ->
            http.get(FILE) {
                parameter("method", "meta")
                parameter("access_token", client.accessToken())
                parameter("path", appDataFolder(path = path))
            }.body()
        }
    }

    /**
     * 获取文件元数据
     * @param paths 文件路径列表
     */
    public suspend fun meta(vararg paths: String): PCSFileList<PCSFile> {
        return client.useHttpClient { http ->
            http.submitFormWithBinaryData(FILE, formData {
                append(
                    key = "param".quote(),
                    value = buildJsonObject {
                        putJsonArray("list") {
                            paths.forEach { path ->
                                addJsonObject {
                                    put("path", appDataFolder(path = path))
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

    /**
     * 列出文件
     * @param path 工作目录
     * @param order 排序方式
     * @param desc 逆序
     * @param limit INDEX区间
     */
    public suspend fun list(path: String, order: OrderType, desc: Boolean, limit: IntRange?): PCSFileList<PCSFile> {
        return client.useHttpClient { http ->
            http.get(FILE) {
                parameter("method", "list")
                parameter("access_token", client.accessToken())
                parameter("path", appDataFolder(path = path))
                parameter("by", order)
                parameter("order", if (desc) "desc" else "asc")
                parameter("limit", limit?.run { "${first}-${last + 1}" })
            }.body()
        }
    }

    /**
     * 移动文件-单个
     * @param from 起始目录
     * @param to 目标目录
     */
    public suspend fun move(from: String, to: String): PCSExtra<PCSFileChange> {
        return client.useHttpClient { http ->
            http.post(FILE) {
                parameter("method", "move")
                parameter("access_token", client.accessToken())
                parameter("from", appDataFolder(path = from))
                parameter("to", appDataFolder(path = to))
            }.body()
        }
    }

    /**
     * 移动文件-多个
     * @param pairs 文件对列表
     */
    public suspend fun move(vararg pairs: Pair<String, String>): PCSExtra<PCSFileChange> {
        return client.useHttpClient { http ->
            http.submitFormWithBinaryData(FILE, formData {
                append(
                    key = "param".quote(),
                    value = buildJsonObject {
                        putJsonArray("list") {
                            pairs.forEach { (from, to) ->
                                addJsonObject {
                                    put("from", appDataFolder(path = from))
                                    put("to", appDataFolder(path = to))
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

    /**
     * 复制文件-单个
     * @param from 起始目录
     * @param to 目标目录
     */
    public suspend fun copy(from: String, to: String): PCSExtra<PCSFileChange> {
        return client.useHttpClient { http ->
            http.post(FILE) {
                parameter("method", "copy")
                parameter("access_token", client.accessToken())
                parameter("from", appDataFolder(path = from))
                parameter("to", appDataFolder(path = to))
            }.body()
        }
    }

    /**
     * 复制文件-多个
     * @param pairs 文件对列表
     */
    public suspend fun copy(vararg pairs: Pair<String, String>): PCSExtra<PCSFileChange> {
        return client.useHttpClient { http ->
            http.submitFormWithBinaryData(FILE, formData {
                append(
                    key = "param".quote(),
                    value = buildJsonObject {
                        putJsonArray("list") {
                            pairs.forEach { (from, to) ->
                                addJsonObject {
                                    put("from", appDataFolder(path = from))
                                    put("to", appDataFolder(path = to))
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

    /**
     * 删除文件-单个
     * @param path 文件路径
     */
    public suspend fun delete(path: String): PCSError {
        return client.useHttpClient { http ->
            http.post(FILE) {
                parameter("method", "delete")
                parameter("access_token", client.accessToken())
                parameter("path", appDataFolder(path = path))
            }.body()
        }
    }

    /**
     * 删除文件-多个
     * @param paths 文件路径列表
     */
    public suspend fun delete(vararg paths: String): PCSError {
        return client.useHttpClient { http ->
            http.submitFormWithBinaryData(FILE, formData {
                append(
                    key = "param".quote(),
                    value = buildJsonObject {
                        putJsonArray("list") {
                            paths.forEach { path ->
                                addJsonObject {
                                    put("path", appDataFolder(path = path))
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

    /**
     * 搜索文件
     * @param path 工作目录
     * @param word 关键词
     * @param recursion 迭代查找
     */
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

    /**
     * 列出回收站文件
     * @param start 页面起始INDEX
     * @param limit 页面文件数上限
     */
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

    /**
     * 恢复回收站文件
     * @param id 文件ID
     */
    public suspend fun restore(id: Long): PCSExtra<PCSFileRestore> {
        return client.useHttpClient { http ->
            http.post(FILE) {
                parameter("method", "restore")
                parameter("access_token", client.accessToken())
                parameter("fs_id", id)
            }.body()
        }
    }


    /**
     * 恢复回收站文件
     * @param ids 文件ID列表
     */
    public suspend fun restore(vararg ids: Long): PCSExtra<PCSFileRestore> {
        return client.useHttpClient { http ->
            http.submitFormWithBinaryData(FILE, formData {
                append(
                    key = "param".quote(),
                    value = buildJsonObject {
                        putJsonArray("list") {
                            ids.forEach { id ->
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

    /**
     * 清空回收站
     */
    public suspend fun clear(): PCSError {
        return client.useHttpClient { http ->
            http.post(FILE) {
                parameter("method", "delete")
                parameter("access_token", client.accessToken())
                parameter("type", "recycle")
            }.body()
        }
    }

    /**
     * 获取 PCS Host
     */
    public suspend fun host(): PCSServer {
        return client.useHttpClient { http ->
            http.post(FILE) {
                parameter("method", "locateupload")
                parameter("access_token", client.accessToken())
            }.body()
        }
    }
}