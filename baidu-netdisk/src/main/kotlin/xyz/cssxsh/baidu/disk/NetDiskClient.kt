package xyz.cssxsh.baidu.disk

import xyz.cssxsh.baidu.oauth.*
import java.nio.file.*

public interface NetDiskClient : BaiduUserAuthClient<BaiduAuthConfig> {
    public val appDataFolder: Path

    public fun appDataFolder(path: String): String = appDataFolder.resolve(path).joinToString("/")

    public companion object {
        public const val TIMEOUT: Long = 30 * 1000L
    }
}