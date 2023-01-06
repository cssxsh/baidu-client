package xyz.cssxsh.baidu.disk

import io.ktor.client.content.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import xyz.cssxsh.baidu.disk.data.*
import xyz.cssxsh.baidu.oauth.*
import xyz.cssxsh.baidu.oauth.data.*
import java.io.*

/**
 * 百度网盘 抽象类
 * 实现 NetDiskClient 接口应继承这个抽象类
 * @see NetDiskClient
 */
public abstract class AbstractNetDiskClient : NetDiskClient, AbstractBaiduAuthClient<BaiduNetDiskConfig>() {

    public abstract val pcs: BaiduPersonalCloudStorage

    public abstract val rest: BaiduNetDiskRESTful

    public abstract val web: BaiduNetDiskWeb

    protected open var user: NetDiskUserInfo? = null

    public abstract suspend fun user(flush: Boolean = true): NetDiskUserInfo

    public abstract suspend fun mkdir(path: String): NetDiskFileInfo

    public abstract suspend fun upload(file: File, path: String, ondup: OnDupType = OnDupType.FAIL): NetDiskFileInfo

    public abstract suspend fun single(file: File, path: String, ondup: OnDupType = OnDupType.FAIL): NetDiskFileInfo

    public abstract suspend fun rapid(upload: RapidUploadInfo, ondup: OnDupType = OnDupType.FAIL): NetDiskFileInfo

    public abstract suspend fun flash(path: String): RapidUploadInfo

    public abstract suspend fun copy(path: String, dest: String, newname: String, ondup: OnDupType): NetDiskOpera

    public abstract suspend fun move(path: String, dest: String, newname: String, ondup: OnDupType): NetDiskOpera

    public abstract suspend fun rename(path: String, newname: String, ondup: OnDupType): NetDiskOpera

    public abstract suspend fun delete(path: String): NetDiskOpera

    public abstract suspend fun search(key: String, dir: String, page: Int): NetDiskFileList

    public abstract suspend fun list(path: String, start: Int): NetDiskFileList

    public abstract suspend fun link(file: NetDiskFileInfo, block: HttpRequestBuilder.() -> Unit): HttpStatement

    public abstract suspend fun download(file: NetDiskFileInfo, range: IntRange? = null): ByteArray

    public abstract suspend fun download(file: NetDiskFileInfo, listener: ProgressListener): ByteArray

    public abstract suspend fun share(password: String, option: ShareOption, vararg files: Long): NetDiskShare

    public abstract suspend fun share(page: Int): NetDiskShareList

    public abstract suspend fun verify(surl: String, password: String): NetDiskShareInfo

    public abstract suspend fun view(surl: String, key: String): NetDiskViewList

    public abstract suspend fun transfer(info: TransferFileInfo, path: String, ondup: OnDupType): NetDiskTransfer

    public abstract suspend fun recycle(page: Int): NetDiskRecycleList

    public abstract suspend fun restore(vararg files: Long): NetDiskRecycleOpera

    public abstract suspend fun clear(): NetDiskRecycleOpera

    override val timeout: Long get() = NetDiskClient.TIMEOUT

    override val userAgent: String get() = NetDiskClient.USER_AGENT

    override val scope: List<String> get() = status.scope.ifEmpty { listOf(ScopeType.BASIC, ScopeType.NET_DISK) }
}