package xyz.cssxsh.baidu

import xyz.cssxsh.baidu.oauth.*

open class BaiduAipClient(config: BaiduAuthConfig) : AbstractAipClient(), BaiduAuthConfig by config {
    constructor(config: BaiduAuthConfig, token: AuthorizeAccessToken) : this(config) {
        save(token)
    }
}