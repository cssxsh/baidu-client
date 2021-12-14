package xyz.cssxsh.baidu

import xyz.cssxsh.baidu.disk.*
import xyz.cssxsh.baidu.oauth.*

abstract class AbstractNetDiskClient : NetDiskClient, AbstractBaiduAuthClient() {

    override val timeout: Long get() = NetDiskClient.Timeout

    override val userAgent: String get() = USER_AGENT

    override var scope = listOf(ScopeType.BASIC, ScopeType.NET_DISK)
        protected set

    override val appDataFolder: String
        get() = "/apps/$appName"
}