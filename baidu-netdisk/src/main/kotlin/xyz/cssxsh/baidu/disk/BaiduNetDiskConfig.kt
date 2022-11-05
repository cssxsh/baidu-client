package xyz.cssxsh.baidu.disk

import xyz.cssxsh.baidu.oauth.*
import java.nio.file.Path
import kotlin.io.path.*

/**
 * 百度网盘 配置接口
 * @property shareSecret 分享功能相关
 * @property shareThirdId 分享功能相关
 */
public interface BaiduNetDiskConfig : BaiduAuthConfig {
    public val shareSecret: String get() = ""
    public val shareThirdId: Long get() = 0

    /**
     * appDataFolder 解析工作目录下路径
     * @param path 相对路径
     */
    public fun appDataFolder(path: String = ""): String =
        Path("apps", appName).resolve(path).joinToString(prefix = "/", separator = "/")

    /**
     * appDataFolder 解析工作目录下路径
     * @param path 相对路径
     */
    public fun appDataFolder(path: Path): String =
        Path("apps", appName).resolve(path).joinToString(prefix = "/", separator = "/")
}