package xyz.cssxsh.baidu.disk

import xyz.cssxsh.baidu.oauth.*
import xyz.cssxsh.baidu.oauth.data.*

/**
 * 百度网盘 抽象类
 * 实现 NetDiskClient 接口应继承这个抽象类
 * @see NetDiskClient
 */
public abstract class AbstractNetDiskClient : NetDiskClient, AbstractBaiduAuthClient<BaiduNetDiskConfig>() {

    override val timeout: Long get() = NetDiskClient.TIMEOUT

    override val userAgent: String get() = NetDiskClient.USER_AGENT

    override val scope: List<String> get() = status.scope.ifEmpty { listOf(ScopeType.BASIC, ScopeType.NET_DISK) }
}