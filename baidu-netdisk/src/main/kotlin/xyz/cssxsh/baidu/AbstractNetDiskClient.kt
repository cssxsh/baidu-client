package xyz.cssxsh.baidu

import xyz.cssxsh.baidu.disk.*
import xyz.cssxsh.baidu.oauth.*
import java.nio.file.*
import kotlin.io.path.*

public abstract class AbstractNetDiskClient : NetDiskClient, AbstractBaiduAuthClient() {

    override val timeout: Long get() = NetDiskClient.Timeout

    override val userAgent: String get() = USER_AGENT

    override var scope: List<ScopeType> = listOf(ScopeType.BASIC, ScopeType.NET_DISK)
        protected set

    override val appDataFolder: Path
        get() = Path("/apps/$appName")
}