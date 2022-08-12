package xyz.cssxsh.baidu.disk

import xyz.cssxsh.baidu.oauth.*

public interface BaiduNetDiskConfig : BaiduAuthConfig {
    public val shareSecret: String get() = ""
    public val shareThirdId: Long get() = 0
}