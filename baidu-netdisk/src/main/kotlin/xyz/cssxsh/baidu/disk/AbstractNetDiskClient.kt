package xyz.cssxsh.baidu.disk

import xyz.cssxsh.baidu.oauth.*
import xyz.cssxsh.baidu.oauth.data.*

public abstract class AbstractNetDiskClient : NetDiskClient, AbstractBaiduAuthClient<BaiduNetDiskConfig>() {

    override val timeout: Long get() = NetDiskClient.TIMEOUT

    override val userAgent: String get() = NetDiskClient.USER_AGENT

    override val scope: List<String> get() = status.scope.ifEmpty { listOf(ScopeType.BASIC, ScopeType.NET_DISK) }
}