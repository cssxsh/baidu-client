package xyz.cssxsh.baidu.disk

import xyz.cssxsh.baidu.oauth.*
import java.nio.file.Path
import kotlin.io.path.*

public interface BaiduNetDiskConfig : BaiduAuthConfig {
    public val shareSecret: String get() = ""
    public val shareThirdId: Long get() = 0

    public fun appDataFolder(path: String = ""): String =
        Path("apps", appName).resolve(path).joinToString(prefix = "/", separator = "/")

    public fun appDataFolder(path: Path): String =
        Path("apps", appName).resolve(path).joinToString(prefix = "/", separator = "/")
}