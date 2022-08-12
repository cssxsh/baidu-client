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
 * Personal Cloud Storage
 * [overview](https://openapi.baidu.com/wiki/index.php?title=docs/pcs/overview)
 * [文件API列表](https://openapi.baidu.com/wiki/index.php?title=docs/pcs/rest/file_data_apis_error)
 */
public class BaiduNetDiskRESTful internal constructor(public val client: NetDiskClient) {
    public companion object {
        internal const val NAS: String = "https://pan.baidu.com/rest/2.0/xpan/nas"
        internal const val FILE: String = "https://pan.baidu.com/rest/2.0/xpan/file"
        internal const val MULTIMEDIA: String = "https://pan.baidu.com/rest/2.0/xpan/multimedia"
        internal const val SHARE: String = "https://pan.baidu.com/rest/2.0/xpan/share"
    }

    /**
     * [获取用户信息](https://pan.baidu.com/union/doc/pksg0s9ns)
     */
    public suspend fun user(): NetDiskUserInfo {
        return client.useHttpClient { http ->
            http.get {
                url(NAS)
                parameter("method", "uinfo")
                parameter("access_token", client.accessToken())
            }.body()
        }
    }

    /**
     * [获取文件列表](https://pan.baidu.com/union/doc/nksg0sat9)
     * @param dir 起始目录, `/` 网盘根目录, 为空时是 APP 数据目录
     * @param order 排序字段
     * @param desc 是否为降序
     * @param page 页码 从 1 开始，一页数目 1000
     * @see NetDiskClient.appDataFolder
     */
    public suspend fun list(dir: String, order: OrderType, desc: Boolean, page: Int): NetDiskFileList {
        return client.useHttpClient { http ->
            http.get {
                url(FILE)
                parameter("method", "list")
                parameter("access_token", client.accessToken())
                parameter("dir", client.appDataFolder(path = dir))
                parameter("order", order.value)
                parameter("desc", desc.toInt())
                parameter("start", (page - 1) * 1_000)
                parameter("limit", 1_000)
                parameter("web", "1")
                parameter("folder", "0")
                parameter("showempty", "1")
            }.body()
        }
    }

    /**
     * [获取文件列表-文件夹](https://pan.baidu.com/union/doc/nksg0sat9)
     * @param dir 起始目录, `/` 网盘根目录, 为空时是 APP 数据目录
     * @param order 排序字段
     * @param desc 是否为降序
     * @param page 页码 从 1 开始，一页数目 1000
     * @see NetDiskClient.appDataFolder
     */
    public suspend fun folder(dir: String, order: OrderType, desc: Boolean, page: Int): NetDiskFileList {
        return client.useHttpClient { http ->
            http.get {
                url(FILE)
                parameter("method", "list")
                parameter("access_token", client.accessToken())
                parameter("dir", client.appDataFolder(path = dir))
                parameter("order", order.value)
                parameter("desc", desc.toInt())
                parameter("start", (page - 1) * 1_000)
                parameter("limit", 1_000)
                parameter("web", "1")
                parameter("folder", "1")
                parameter("showempty", "1")
            }.body()
        }
    }

    /**
     * [递归获取文件列表](https://pan.baidu.com/union/doc/Zksg0sb73)
     * @param start 起始 index, 从 0 开始
     * @param option 排序方式和起始路径
     * @see NetDiskClient.appDataFolder
     * @see NetDiskFileList.cursor
     */
    public suspend fun all(start: Int, option: NetDiskOption): NetDiskFileList {
        return client.useHttpClient { http ->
            http.get {
                url(MULTIMEDIA)
                parameter("method", "listall")
                parameter("access_token", client.accessToken())
                parameter("path", client.appDataFolder(path = option.path))
                parameter("start", start)
                parameter("limit", 1_000)
                parameter("order", option.order.value)
                parameter("desc", option.desc.toInt())
                parameter("recursion", option.recursion.toInt())
                parameter("ctime", option.created?.toEpochSecond())
                parameter("mtime", option.modified?.toEpochSecond())
                parameter("web", "1")
                parameter("show_dir", "1")
                parameter("showempty", "1")
            }.body()
        }
    }

    /**
     * [获取分类列表文件](https://pan.baidu.com/union/doc/Sksg0sb40)
     * @param categories 文件类型
     * @param start 起始 index, 从 0 开始
     * @param option 排序和时间过滤
     * @see NetDiskFileList.cursor
     */
    public suspend fun category(vararg categories: CategoryType, start: Int, option: NetDiskOption): NetDiskFileList {
        check(categories.isNotEmpty()) { "categories is empty" }
        return client.useHttpClient { http ->
            http.get {
                url(MULTIMEDIA)
                parameter("method", "categorylist")
                parameter("access_token", client.accessToken())
                parameter("category", categories.joinToString(",") { it.ordinal.toString() })
                parameter("parent_path", client.appDataFolder(path = option.path))
                parameter("start", start)
                parameter("limit", 1_000)
                parameter("order", option.order)
                parameter("desc", option.desc.toInt())
                parameter("recursion", option.recursion.toInt())
                parameter("ctime", option.created?.toEpochSecond())
                parameter("mtime", option.modified?.toEpochSecond())
                parameter("web", "1")
                parameter("ext", option.formats?.joinToString(","))
            }.body()
        }
    }

    /**
     * [查询文件信息](https://pan.baidu.com/union/doc/Fksg0sbcm)
     * @param ids 文件id数组，数组中元素是uint64类型，数组大小上限是：100
     * @param path 查询共享目录或专属空间内文件时需要。
     * * 共享目录格式：/uk-fsid 其中uk为共享目录创建者id，fsid对应共享目录的fsid
     * * 专属空间格式：/_pcs_.appdata/xpan/
     */
    public suspend fun query(ids: List<Long>, path: String): NetDiskQuery {
        return client.useHttpClient { http ->
            http.get {
                url(MULTIMEDIA)
                parameter("method", "filemetas")
                parameter("access_token", client.accessToken())
                parameter("fsids", ids)
                parameter("path", path)
                parameter("thumb", "1")
                parameter("dlink", "1")
                parameter("extra", "1")
                parameter("needmedia", "1")
            }.body()
        }
    }

    /**
     * [获取视频列表](https://pan.baidu.com/union/doc/Sksg0saw0)
     * @param page 页码 从 1 开始，一页数目 1000
     * @param option 排序方式和起始路径
     * @see NetDiskClient.appDataFolder
     */
    public suspend fun video(page: Int, option: NetDiskOption): NetDiskCategoryList {
        return client.useHttpClient { http ->
            http.get {
                url(FILE)
                parameter("method", "videolist")
                parameter("access_token", client.accessToken())
                parameter("parent_path", client.appDataFolder(path = option.path))
                parameter("page", page)
                parameter("num", 1_000)
                parameter("order", option.order.value)
                parameter("desc", option.desc.toInt())
                parameter("recursion", option.recursion.toInt())
                parameter("web", "1")
            }.body()
        }
    }

    /**
     * 获取音频列表
     * @param page 页码 从 1 开始，一页数目 1000
     * @param option 排序方式和起始路径
     * @see NetDiskClient.appDataFolder
     */
    public suspend fun audio(page: Int, option: NetDiskOption): NetDiskCategoryList {
        return client.useHttpClient { http ->
            http.get {
                url(FILE)
                parameter("method", "audiolist")
                parameter("access_token", client.accessToken())
                parameter("parent_path", client.appDataFolder(path = option.path))
                parameter("page", page)
                parameter("num", 1_000)
                parameter("order", option.order.value)
                parameter("desc", option.desc.toInt())
                parameter("recursion", option.recursion.toInt())
                parameter("web", "1")
            }.body()
        }
    }

    /**
     * [获取图片列表](https://pan.baidu.com/union/doc/bksg0sayv)
     * @param page 页码 从 1 开始，一页数目 1000
     * @param option 排序方式和起始路径
     * @see NetDiskClient.appDataFolder
     */
    public suspend fun image(page: Int, option: NetDiskOption): NetDiskCategoryList {
        return client.useHttpClient { http ->
            http.get {
                url(FILE)
                parameter("method", "imagelist")
                parameter("access_token", client.accessToken())
                parameter("parent_path", client.appDataFolder(path = option.path))
                parameter("page", page)
                parameter("num", 1_000)
                parameter("order", option.order.value)
                parameter("desc", option.desc.toInt())
                parameter("recursion", option.recursion.toInt())
                parameter("web", "1")
            }.body()
        }
    }

    /**
     * [获取文档列表](https://pan.baidu.com/union/doc/Eksg0saqp)
     * @param page 页码 从 1 开始，一页数目 1000
     * @param option 排序方式和起始路径
     * @see NetDiskClient.appDataFolder
     */
    public suspend fun document(page: Int, option: NetDiskOption): NetDiskCategoryList {
        return client.useHttpClient { http ->
            http.get {
                url(FILE)
                parameter("method", "doclist")
                parameter("access_token", client.accessToken())
                parameter("parent_path", client.appDataFolder(path = option.path))
                parameter("page", page)
                parameter("num", 1_000)
                parameter("order", option.order.value)
                parameter("desc", option.desc.toInt())
                parameter("recursion", option.recursion.toInt())
                parameter("web", "1")
            }.body()
        }
    }

    /**
     * [获取bt列表](https://pan.baidu.com/union/doc/xksg0sb1d)
     * @param page 页码 从 1 开始，一页数目 1000
     * @param option 排序方式和起始路径
     * @see NetDiskClient.appDataFolder
     */
    public suspend fun bittorrent(page: Int, option: NetDiskOption): NetDiskCategoryList {
        return client.useHttpClient { http ->
            http.get {
                url(FILE)
                parameter("method", "btlist")
                parameter("access_token", client.accessToken())
                parameter("parent_path", client.appDataFolder(path = option.path))
                parameter("page", page)
                parameter("num", 1_000)
                parameter("order", option.order.value)
                parameter("desc", option.desc.toInt())
                parameter("recursion", option.recursion.toInt())
                parameter("web", "1")
            }.body()
        }
    }

    /**
     * [搜索文件](https://pan.baidu.com/union/doc/zksg0sb9z)
     * @param key 关键词
     * @param dir 起始目录, `/` 网盘根目录, 为空时是 APP 数据目录
     * @param recursion 是否递归
     * @param page 页码 从 1 开始，一页数目 1000
     * @see NetDiskClient.appDataFolder
     */
    public suspend fun search(key: String, dir: String, recursion: Boolean, page: Int): NetDiskFileList {
        return client.useHttpClient { http ->
            http.get {
                url(FILE)
                parameter("method", "search")
                parameter("access_token", client.accessToken())
                parameter("key", key)
                parameter("dir", client.appDataFolder(path = dir))
                parameter("recursion", recursion.toInt())
                parameter("page", page)
                parameter("num", 1_000)
                parameter("web", "1")
            }.body()
        }
    }

    /**
     * [管理文件-复制](https://pan.baidu.com/union/doc/mksg0s9l4)
     * @param operations 文件操作
     * @see NetDiskClient.appDataFolder
     */
    public suspend fun copy(vararg operations: OperaFileInfo, ondup: OnDupType): NetDiskOpera {
        return client.useHttpClient { http ->
            http.submitForm(Parameters.build {
                appendParameter("async", AsyncType.SYNC.ordinal)
                appendParameter("filelist", BaiduJson.encodeToString(operations.map { operation ->
                    operation.copy(
                        path = client.appDataFolder(path = operation.path),
                        dest = client.appDataFolder(path = operation.dest)
                    )
                }))
                appendParameter("ondup", ondup)
            }) {
                url(FILE)
                parameter("method", "filemanager")
                parameter("access_token", client.accessToken())
                parameter("opera", "copy")
            }.body()
        }
    }

    /**
     * [管理文件-移动](https://pan.baidu.com/union/doc/mksg0s9l4)
     * @param operations 文件操作
     * @see NetDiskClient.appDataFolder
     */
    public suspend fun move(vararg operations: OperaFileInfo, ondup: OnDupType): NetDiskOpera {
        return client.useHttpClient { http ->
            http.submitForm(Parameters.build {
                appendParameter("async", AsyncType.SYNC.ordinal)
                appendParameter("filelist", BaiduJson.encodeToString(operations.map { operation ->
                    operation.copy(
                        path = client.appDataFolder(path = operation.path),
                        dest = client.appDataFolder(path = operation.dest)
                    )
                }))
                appendParameter("ondup", ondup)
            }) {
                url(FILE)
                parameter("method", "filemanager")
                parameter("access_token", client.accessToken())
                parameter("opera", "move")
            }.body()
        }
    }

    /**
     * [管理文件-重命名](https://pan.baidu.com/union/doc/mksg0s9l4)
     * @param operations 文件操作
     * @see NetDiskClient.appDataFolder
     */
    public suspend fun rename(vararg operations: OperaFileInfo, ondup: OnDupType): NetDiskOpera {
        return client.useHttpClient { http ->
            http.submitForm(Parameters.build {
                appendParameter("async", AsyncType.SYNC.ordinal)
                appendParameter("filelist", BaiduJson.encodeToString(operations.map { operation ->
                    operation.copy(
                        path = client.appDataFolder(path = operation.path),
                        dest = client.appDataFolder(path = operation.dest)
                    )
                }))
                appendParameter("ondup", ondup)
            }) {
                url(FILE)
                parameter("method", "filemanager")
                parameter("access_token", client.accessToken())
                parameter("opera", "rename")
            }.body()
        }
    }

    /**
     * [管理文件-删除](https://pan.baidu.com/union/doc/mksg0s9l4)
     * @param operations 文件操作
     * @see NetDiskClient.appDataFolder
     */
    public suspend fun delete(vararg operations: OperaFileInfo): NetDiskOpera {
        return client.useHttpClient { http ->
            http.submitForm(Parameters.build {
                appendParameter("async", AsyncType.SYNC.ordinal)
                appendParameter("filelist", BaiduJson.encodeToString(operations.map { operation ->
                    operation.copy(path = client.appDataFolder(path = operation.path))
                }))
                appendParameter("ondup", OnDupType.FAIL)
            }) {
                url(FILE)
                parameter("method", "filemanager")
                parameter("access_token", client.accessToken())
                parameter("opera", "delete")
            }.body()
        }
    }

    /**
     * [预上传](https://pan.baidu.com/union/doc/3ksg0s9r7)
     * @param upload 文件信息
     * @param blocks 文件各分片MD5数组的json串, 不想计算可以使用 [LAZY_BLOCKS]
     * @param ondup 文件冲突策略
     * @see LAZY_BLOCKS 不想计算 blocks 可以用这个，相当于标记所有块都上传，然后由服务器计算md5。
     * @see create
     * @see BaiduPersonalCloudStorage.temp
     */
    public suspend fun prepare(upload: RapidUploadInfo, blocks: List<String>, ondup: OnDupType): NetDiskPrepareInfo {
        return client.useHttpClient { http ->
            http.submitForm(Parameters.build {
                appendParameter("path", client.appDataFolder(path = upload.path))
                appendParameter("size", upload.length)
                appendParameter("isdir", "0")
                appendParameter("autoinit", "1")
                appendParameter("block_list", BaiduJson.encodeToString(blocks))
                appendParameter("content-md5", upload.content)
                appendParameter("slice-md5", upload.slice)
                appendParameter("local_ctime", upload.created?.toEpochSecond())
                appendParameter("local_mtime", upload.modified?.toEpochSecond())
                appendParameter("rtype", ondup.ordinal)
                // appendParameter("uploadid", uploadId)
            }) {
                url(FILE)
                parameter("method", "precreate")
                parameter("access_token", client.accessToken())
            }.body()
        }
    }

    /**
     * [创建文件](https://pan.baidu.com/union/doc/rksg0sa17)
     * @param merge 合并信息
     * @param ondup 文件冲突策略
     * @see prepare
     * @see BaiduPersonalCloudStorage.temp
     */
    public suspend fun create(merge: MergeFileInfo, ondup: OnDupType): NetDiskCreateFile {
        return client.useHttpClient { http ->
            http.submitForm(Parameters.build {
                appendParameter("path", client.appDataFolder(path = merge.path))
                appendParameter("size", merge.size)
                appendParameter("isdir", "0")
                appendParameter("rtype", ondup.ordinal)
                appendParameter("uploadid", merge.uploadId)
                appendParameter("autoinit", "1")
                appendParameter("block_list", BaiduJson.encodeToString(merge.blocks))
                // appendParameter("is_revision", isRevision?.toInt())
                // appendParameter("exif_info", exifInfo)
                appendParameter("local_ctime", merge.created?.toEpochSecond())
                appendParameter("local_mtime", merge.modified?.toEpochSecond())
                // appendParameter("zip_quality", zipQuality)
                // appendParameter("zip_sign", zipSign)
                // appendParameter("mode", mode)
            }) {
                url(FILE)
                parameter("method", "create")
                parameter("access_token", client.accessToken())
            }.body()
        }
    }

    /**
     * [创建文件-文件夹](https://pan.baidu.com/union/doc/rksg0sa17)
     * @param path 文件路径
     * @param ondup 文件冲突策略
     */
    public suspend fun mkdir(path: String, ondup: OnDupType): NetDiskCreateFile {
        return client.useHttpClient { http ->
            http.submitForm(Parameters.build {
                appendParameter("path", client.appDataFolder(path = path))
                appendParameter("size", 0)
                appendParameter("isdir", "1")
                appendParameter("rtype", ondup.ordinal)
                // appendParameter("uploadid", uploadId)
                appendParameter("autoinit", "1")
                // appendParameter("block_list", "[]")
            }) {
                url(FILE)
                parameter("method", "create")
                parameter("access_token", client.accessToken())
            }.body()
        }
    }

    /**
     * [分享提取码验证](https://pan.baidu.com/union/doc/Yksmyl2v0)
     * @param surl 创建附件接口返回的 short url 最后一个斜杠之后的字符串, 或者 surl 参数
     * @param password 访问密码
     */
    public suspend fun verify(surl: String, password: String): NetDiskShareInfo {
        return client.useHttpClient { http ->
            http.submitForm(Parameters.build {
                appendParameter("pwd", password)
                appendParameter("third_type", shareThirdId)
                appendParameter("redirect", "0")
            }) {
                url(SHARE)
                header(HttpHeaders.Referrer, "pan.baidu.com")
                parameter("method", "verify")
                parameter("access_token", client.accessToken())
                parameter("surl", surl)
            }.body()
        }
    }

    /**
     * [获取分享文件信息](https://pan.baidu.com/union/doc/3ksmyma1y)
     * @param surl 创建附件接口返回的 short url 最后一个斜杠之后的字符串, 或者 surl 参数
     * @param key 在 [verify] 中得到的 key
     * @param fid 文件夹 id，为 0 时将显示 root 目录
     * @param page 页码 从 1 开始，一页数目 1000
     */
    public suspend fun view(surl: String, key: String, fid: Long, page: Int): NetDiskViewList {
        return client.useHttpClient { http ->
            http.get {
                url(SHARE)
                header(HttpHeaders.Referrer, "pan.baidu.com")
                parameter("method", "list")
                parameter("access_token", client.accessToken())
                parameter("shorturl", surl)
                parameter("sekey", key)
                parameter("page", page)
                parameter("num", 1000)
                parameter("web", "1")
                parameter("root", "1")
                parameter("fid", fid)
            }.body()
        }
    }

    /**
     * [分享文件转存](https://pan.baidu.com/union/doc/xksmyoqgv)
     * @param info 转存信息
     * @param path 文件存放目录
     * @param ondup 遇到重复文件的处理策略
     */
    public suspend fun transfer(info: TransferFileInfo, path: String, ondup: OnDupType): NetDiskTransfer {
        return client.useHttpClient { http ->
            http.submitForm(Parameters.build {
                appendParameter("async", AsyncType.SYNC.ordinal)
                appendParameter("path", client.appDataFolder(path = path))
                appendParameter("fsidlist", info.files)
                appendParameter("ondup", ondup)
            }) {
                url(SHARE)
                header(HttpHeaders.Referrer, "pan.baidu.com")
                parameter("method", "transfer")
                parameter("access_token", client.accessToken())
                parameter("shareid", info.shareId)
                parameter("from", info.from)
                parameter("sekey", info.key)
                parameter("web", "1")
            }.body()
        }
    }
}