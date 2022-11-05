package xyz.cssxsh.baidu.disk

import xyz.cssxsh.baidu.oauth.*

/**
 * 百度网盘 客户端接口
 * @see AbstractNetDiskClient
 * @see BaiduNetDiskConfig
 */
public interface NetDiskClient : BaiduUserAuthClient<BaiduNetDiskConfig> {

    public companion object {
        public const val TIMEOUT: Long = 30 * 1000L
        public const val USER_AGENT: String = "pan.baidu.com"
        public const val INDEX_PAGE: String = "https://pan.baidu.com/"
    }
}