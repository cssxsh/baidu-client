package xyz.cssxsh.baidu.disk

import xyz.cssxsh.baidu.oauth.*
import java.nio.file.*

public interface NetDiskClient : BaiduUserAuthClient<BaiduNetDiskConfig> {
    public val appDataFolder: Path

    /**
     * 将相对路径变成绝对路径
     */
    public fun appDataFolder(path: String): String =
        appDataFolder.resolve(path).joinToString(prefix = "/", separator = "/")

    public companion object {
        public const val TIMEOUT: Long = 30 * 1000L
        public const val USER_AGENT: String = "pan.baidu.com"
        public const val INDEX_PAGE: String = "https://pan.baidu.com/"
    }
}