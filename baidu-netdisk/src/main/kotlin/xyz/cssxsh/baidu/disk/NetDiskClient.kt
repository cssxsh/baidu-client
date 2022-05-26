package xyz.cssxsh.baidu.disk

import xyz.cssxsh.baidu.oauth.*
import java.nio.file.*

public interface NetDiskClient : BaiduUserAuthClient<BaiduAuthConfig> {
    public val appDataFolder: Path

    public fun appDataFolder(path: String): Path = appDataFolder.resolve(path)

    public companion object {
        public const val Timeout: Long = 30 * 1000L
    }
}