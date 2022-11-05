package xyz.cssxsh.baidu.disk

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import xyz.cssxsh.baidu.api.*
import xyz.cssxsh.baidu.disk.data.*

/**
 * 百度云 网页端 API
 */
public class BaiduNetDiskWeb internal constructor(public val client: NetDiskClient) {
    public companion object {
        internal const val QUOTA: String = "https://pan.baidu.com/api/quota"
        internal const val LIST: String = "https://pan.baidu.com/api/list"
        internal const val LOGIN_STATUS = "https://pan.baidu.com/api/loginStatus"
        internal const val CATEGORY_INFO: String = "https://pan.baidu.com/api/categoryinfo"
        internal const val CATEGORY_LIST: String = "https://pan.baidu.com/api/categorylist"
        internal const val SEARCH: String = "https://pan.baidu.com/api/search"
        internal const val PRECREATE: String = "https://pan.baidu.com/api/precreate"
        internal const val CREATE: String = "https://pan.baidu.com/api/create"
        internal const val RAPID_UPLOAD: String = "https://pan.baidu.com/api/rapidupload"
        internal const val FILE_MANAGER = "https://pan.baidu.com/api/filemanager"
        internal const val TASK_QUERY = "https://pan.baidu.com/share/taskquery"
        internal const val SHORT_URL_INFO = "https://pan.baidu.com/api/shorturlinfo"
        internal const val SHARE_SET = "https://pan.baidu.com/share/set"
        internal const val SHARE_RECORD = "https://pan.baidu.com/share/record"
        internal const val SHARE_VERIFY = "https://pan.baidu.com/share/verify"
        internal const val SHARE_LIST = "https://pan.baidu.com/share/list"
        internal const val RECYCLE_LIST = "https://pan.baidu.com/api/recycle/list"
        internal const val RECYCLE_RESTORE = "https://pan.baidu.com/api/recycle/restore"
        internal const val RECYCLE_CLEAR = "https://pan.baidu.com/api/recycle/clear"
    }

    /**
     * 登陆状态
     */
    public suspend fun status(): NetDiskLoginStatus {
        return client.useHttpClient { http ->
            http.get {
                url(LOGIN_STATUS)
                parameter("clienttype", "0")
                parameter("access_token", client.accessToken())
            }.body()
        }
    }

    /**
     * [获取网盘容量信息](https://pan.baidu.com/union/doc/Cksg0s9ic)
     */
    public suspend fun quota(): NetDiskQuotaInfo {
        return client.useHttpClient { http ->
            http.get {
                url(QUOTA)
                parameter("checkfree", "1")
                parameter("checkexpire", "1")
                parameter("access_token", client.accessToken())
            }.body()
        }
    }

    /**
     * 列出文件信息 - 网页
     * @param page 页码 从 1 开始，一页数目 1000
     * @param option 排序方式和起始路径
     * @see BaiduNetDiskConfig.appDataFolder
     */
    public suspend fun list(page: Int, option: NetDiskOption): NetDiskFileList {
        return client.useHttpClient { http ->
            http.get {
                url(LIST)
                parameter("order", option.order)
                parameter("desc", option.desc.toInt())
                parameter("parent_path", appDataFolder(path = option.path))
                parameter("recursion", option.recursion.toInt())
                parameter("page", page)
                parameter("num", 1_000)
                parameter("web", "1")
                parameter("access_token", client.accessToken())
            }.body()
        }
    }

    /**
     * [获取分类文件总个数](https://pan.baidu.com/union/doc/dksg0sanx)
     * @param types 文件类型，置空时返回所有类型
     * @param path 目录
     * @param recursion 是否递归
     */
    public suspend fun categories(vararg types: CategoryType, path: String, recursion: Boolean): NetDiskCategory {
        return client.useHttpClient { http ->
            http.get {
                url(CATEGORY_INFO)
                parameter("category", types.joinToString(",") { it.ordinal.toString() })
                parameter("parent_path", appDataFolder(path = path))
                parameter("recursion", recursion.toInt())
                parameter("access_token", client.accessToken())
            }.body()
        }
    }

    /**
     * 获取分类文件 - 网页
     * @param type 文件类型
     * @param page 页码 从 1 开始，一页数目 1000
     * @param option 排序方式和起始路径
     */
    public suspend fun category(type: CategoryType, page: Int, option: NetDiskOption): NetDiskCategoryList {
        return client.useHttpClient { http ->
            http.get {
                url(CATEGORY_LIST)
                parameter("category", type.ordinal)
                parameter("order", option.order)
                parameter("desc", option.desc.toInt())
                parameter("dir", appDataFolder(path = option.path))
                parameter("recursion", option.recursion.toInt())
                parameter("page", page)
                parameter("num", 1_000)
                parameter("web", "1")
                parameter("access_token", client.accessToken())
            }.body()
        }
    }

    /**
     * 搜索文件 - 网页
     * @param key 关键词
     * @param page 页码 从 1 开始，一页数目 1000
     * @param option 排序方式和起始路径
     */
    public suspend fun search(key: String, page: Int, option: NetDiskOption): NetDiskFileList {
        return client.useHttpClient { http ->
            http.get {
                url(SEARCH)
                parameter("order", option.order)
                parameter("desc", option.desc.toInt())
                parameter("parent_path", appDataFolder(path = option.path))
                parameter("recursion", option.recursion.toInt())
                parameter("page", page)
                parameter("num", 1_000)
                parameter("key", key)
                parameter("web", "1")
                parameter("access_token", client.accessToken())
            }.body()
        }
    }

    /**
     * 管理文件-复制-异步
     * @param operations 文件操作
     * @see task
     * @see BaiduNetDiskConfig.appDataFolder
     */
    public suspend fun copy(vararg operations: OperaFileInfo, ondup: OnDupType): NetDiskOpera {
        return client.useHttpClient { http ->
            http.submitForm(Parameters.build {
                appendParameter("filelist", BaiduJson.encodeToString(operations.map { operation ->
                    operation.copy(
                        path = appDataFolder(path = operation.path),
                        dest = appDataFolder(path = operation.dest)
                    )
                }))
            }) {
                url(FILE_MANAGER)
                parameter("opera", "copy")
                parameter("async", AsyncType.AUTO.ordinal)
                parameter("onnest", ondup)
                parameter("access_token", client.accessToken())
            }.body()
        }
    }

    /**
     * 管理文件-移动-异步
     * @param operations 文件操作
     * @see task
     * @see BaiduNetDiskConfig.appDataFolder
     */
    public suspend fun move(vararg operations: OperaFileInfo, ondup: OnDupType): NetDiskOpera {
        return client.useHttpClient { http ->
            http.submitForm(Parameters.build {
                appendParameter("filelist", BaiduJson.encodeToString(operations.map { operation ->
                    operation.copy(
                        path = appDataFolder(path = operation.path),
                        dest = appDataFolder(path = operation.dest)
                    )
                }))
            }) {
                url(FILE_MANAGER)
                parameter("opera", "move")
                parameter("async", AsyncType.AUTO.ordinal)
                parameter("onnest", ondup)
                parameter("access_token", client.accessToken())
            }.body()
        }
    }

    /**
     * 管理文件-重命名-异步
     * @param operations 文件操作
     * @see task
     * @see BaiduNetDiskConfig.appDataFolder
     */
    public suspend fun rename(vararg operations: OperaFileInfo, ondup: OnDupType): NetDiskOpera {
        return client.useHttpClient { http ->
            http.submitForm(Parameters.build {
                appendParameter("filelist", BaiduJson.encodeToString(operations.map { operation ->
                    operation.copy(
                        path = appDataFolder(path = operation.path),
                        dest = appDataFolder(path = operation.dest)
                    )
                }))
            }) {
                url(FILE_MANAGER)
                parameter("opera", "rename")
                parameter("async", AsyncType.AUTO.ordinal)
                parameter("onnest", ondup)
                parameter("access_token", client.accessToken())
            }.body()
        }
    }

    /**
     * 管理文件-删除-异步
     * @param operations 文件操作
     * @see task
     * @see BaiduNetDiskConfig.appDataFolder
     */
    public suspend fun delete(vararg operations: OperaFileInfo): NetDiskOpera {
        return client.useHttpClient { http ->
            http.submitForm(Parameters.build {
                appendParameter("filelist", BaiduJson.encodeToString(operations.map { operation ->
                    operation.copy(path = appDataFolder(path = operation.path))
                }))
            }) {
                url(FILE_MANAGER)
                parameter("opera", "delete")
                parameter("async", AsyncType.AUTO.ordinal)
                parameter("access_token", client.accessToken())
            }.body()
        }
    }

    /**
     * 快速上传 - 网页
     * @param upload 文件信息
     */
    public suspend fun rapid(upload: RapidUploadInfo): NetDiskRapidInfo {
        return client.useHttpClient { http ->
            http.submitForm(Parameters.build {
                appendParameter("path", appDataFolder(path = upload.path))
                appendParameter("content-md5", upload.content)
                appendParameter("slice-md5", upload.slice)
                appendParameter("content-length", upload.length)
            }) {
                url(RAPID_UPLOAD)
                parameter("access_token", client.accessToken())
            }.body()
        }
    }

    /**
     * 预上传 - 网页
     * @param upload 文件信息
     * @param blocks 文件各分片MD5数组的json串, 不想计算可以使用 [LAZY_BLOCKS]
     * @see LAZY_BLOCKS 不想计算 blocks 可以用这个，相当于标记所有块都上传，然后由服务器计算md5。
     * @see create
     * @see BaiduPersonalCloudStorage.temp
     */
    public suspend fun prepare(upload: RapidUploadInfo, blocks: List<String>): NetDiskPrepareInfo {
        return client.useHttpClient { http ->
            http.submitForm(Parameters.build {
                appendParameter("content-md5", upload.content)
                appendParameter("slice-md5", upload.slice)
                appendParameter("content-length", upload.length)
                appendParameter("path", appDataFolder(path = upload.path))
                appendParameter("block_list", BaiduJson.encodeToString(blocks))
                appendParameter("local_ctime", upload.created?.toEpochSecond())
                appendParameter("local_mtime", upload.modified?.toEpochSecond())
                appendParameter("autoinit", "1")
            }) {
                url(PRECREATE)
                parameter("method", "precreate")
                parameter("access_token", client.accessToken())
            }.body()
        }
    }

    /**
     * 创建文件 - 网页
     * @param merge 合并信息
     * @see prepare
     * @see BaiduPersonalCloudStorage.temp
     */
    public suspend fun create(merge: MergeFileInfo): NetDiskCreateFile {
        return client.useHttpClient { http ->
            http.submitForm(Parameters.build {
                appendParameter("isdir", "0")
                appendParameter("size", merge.size)
                appendParameter("uploadid", merge.uploadId)
                appendParameter("path", appDataFolder(path = merge.path))
                appendParameter("block_list", Json.encodeToString(merge.blocks))
                appendParameter("local_ctime", merge.created)
                appendParameter("local_mtime", merge.modified)
                appendParameter("autoinit", "1")
            }) {
                url(CREATE)
                parameter("method", "create")
                parameter("access_token", client.accessToken())
            }.body()
        }
    }

    /**
     * 创建文件-文件夹
     * @param path 文件路径
     */
    public suspend fun mkdir(path: String): NetDiskCreateFile {
        return client.useHttpClient { http ->
            http.submitForm(Parameters.build {
                appendParameter("path", appDataFolder(path = path))
                appendParameter("isdir", "1")
            }) {
                url(CREATE)
                parameter("access_token", client.accessToken())
            }.body()
        }
    }

    /**
     * 异步任务状态查询
     * @param id 任务ID
     * @see NetDiskTaskInfo.taskId
     */
    public suspend fun task(id: Long): NetDiskTask {
        return client.useHttpClient { http ->
            http.get {
                url(TASK_QUERY)
                parameter("taskid", id)
                parameter("clienttype", "0")
                parameter("app_id", appId)
                parameter("web", "1")
                parameter("access_token", client.accessToken())
            }.body()
        }
    }

    /**
     * [查询分享详情](https://pan.baidu.com/union/doc/hksmynu2y)
     * @param surl surl 参数，或者 short url 的 /s/ 之后的字符串 (包含开头的 1)
     * @param key key 参数
     */
    public suspend fun short(surl: String, key: String): NetDiskShortLink {
        return client.useHttpClient { http ->
            http.get {
                url(SHORT_URL_INFO)
                parameter("shorturl", surl)
                parameter("linksource", "")
//                parameter("shareid", "")
//                parameter("uk", "")
                parameter("spd", key)
                parameter("web", "1")
                parameter("clienttype", "0")
                parameter("access_token", client.accessToken())
            }.body()
        }
    }

    /**
     * 创建分享链接
     * @param password 密码
     * @param option 分享选项
     * @param files 要分享文件的ID
     */
    public suspend fun share(password: String, option: ShareOption, vararg files: Long): NetDiskShare {
        return client.useHttpClient { http ->
            http.submitForm(Parameters.build {
                appendParameter("fid_list", files.asList())
                appendParameter("pwd", password)
                appendParameter("period", option.period)
                appendParameter("schannel", option.channel)
                appendParameter("eflag_disable", option.easy)
            }) {
                url(SHARE_SET)
                parameter("clienttype", "0")
                parameter("app_id", appId)
                parameter("web", "1")
                parameter("access_token", client.accessToken())
            }.body()
        }
    }

    /**
     * 查询当前账户分享记录，（此接口返回的 password 为空）
     * @param page 页码 从 1 开始，一页数目 1000
     */
    public suspend fun record(page: Int): NetDiskShareList {
        return client.useHttpClient { http ->
            http.get {
                url(SHARE_RECORD)
                parameter("clienttype", "0")
                parameter("app_id", appId)
                parameter("page", page)
                parameter("num", 1_000)
                parameter("order", "ctime")
                parameter("desc", 1)
                parameter("web", "1")
                parameter("access_token", client.accessToken())
            }.body()
        }
    }

    /**
     * 分享提取码验证
     * @param surl surl 参数，或者 short url 的 /s/ 之后的字符串
     * @param password 访问密码
     */
    public suspend fun verify(surl: String, password: String): NetDiskShareInfo {
        return client.useHttpClient { http ->
            http.submitForm(Parameters.build {
                appendParameter("pwd", password)
            }) {
                url(SHARE_VERIFY)
                header(HttpHeaders.Referrer, NetDiskClient.INDEX_PAGE)
                parameter("method", "verify")
                parameter("access_token", client.accessToken())
                parameter("surl", surl.removePrefix("1"))
            }.body()
        }
    }

    /**
     * 获取分享文件信息-根目录
     * @param surl surl 参数，或者 short url 的 /s/ 之后的字符串
     * @param key 在 [verify] 中得到的 key
     */
    public suspend fun view(surl: String, key: String): NetDiskViewList {
        return client.useHttpClient { http ->
            http.get {
                url(SHARE_LIST)
                parameter("shorturl", surl.removePrefix("1"))
                parameter("sekey", key)
                parameter("web", "1")
                parameter("root", "1")
                parameter("access_token", client.accessToken())
            }.body()
        }
    }

    /**
     * [获取分享文件信息-子文件夹](https://pan.baidu.com/union/doc/3ksmyma1y)
     * @param surl surl 参数，或者 short url 的 /s/ 之后的字符串
     * @param key 在 [verify] 中得到的 key
     * @param page 页码 从 1 开始，一页数目 1000
     * @param option 排序方式和起始路径
     */
    public suspend fun view(surl: String, key: String, page: Int, option: NetDiskOption): NetDiskFileList {
        return client.useHttpClient { http ->
            http.get {
                url(BaiduNetDiskRESTful.SHARE)
                header(HttpHeaders.Referrer, NetDiskClient.INDEX_PAGE)
                parameter("method", "list")
                parameter("access_token", client.accessToken())
                parameter("shorturl", surl.removePrefix("1"))
                parameter("sekey", key)
                parameter("page", page)
                parameter("num", 1_000)
                parameter("web", "1")
                parameter("dir", option.path)
                parameter("order", option.order)
                parameter("desc", option.desc.toInt())
                parameter("recursion", option.recursion.toInt())
                parameter("showempty", "1")
            }.body()
        }
    }

    /**
     * 回收站
     * @param page 页码 从 1 开始，一页数目 1000
     */
    public suspend fun recycle(page: Int): NetDiskRecycleList {
        return client.useHttpClient { http ->
            http.get {
                url(RECYCLE_LIST)
                header(HttpHeaders.Referrer, NetDiskClient.INDEX_PAGE)
                parameter("clienttype", "0")
                parameter("app_id", appId)
                parameter("page", page)
                parameter("num", 1_000)
                parameter("web", "1")
                parameter("access_token", client.accessToken())
            }.body()
        }
    }

    /**
     * 恢复文件-异步
     * @param files 要恢复文件的ID
     */
    public suspend fun restore(vararg files: Long): NetDiskRecycleOpera {
        return client.useHttpClient { http ->
            http.submitForm(Parameters.build {
                appendParameter("fidlist", files.asList())
            }) {
                url(RECYCLE_RESTORE)
                header(HttpHeaders.Referrer, NetDiskClient.INDEX_PAGE)
                parameter("clienttype", "0")
                parameter("app_id", appId)
                parameter("async", AsyncType.AUTO.ordinal)
                parameter("web", "1")
                parameter("access_token", client.accessToken())
            }.body()
        }
    }

    /**
     * 彻底清理回收站文件-异步
     */
    public suspend fun clear(): NetDiskRecycleOpera {
        return client.useHttpClient { http ->
            http.submitForm {
                url(RECYCLE_CLEAR)
                header(HttpHeaders.Referrer, NetDiskClient.INDEX_PAGE)
                header(HttpHeaders.Origin, NetDiskClient.INDEX_PAGE)
                parameter("clienttype", "0")
                parameter("app_id", "250528")
                parameter("async", AsyncType.AUTO.ordinal)
                parameter("web", "1")
                parameter("access_token", client.accessToken())
            }.body()
        }
    }
}