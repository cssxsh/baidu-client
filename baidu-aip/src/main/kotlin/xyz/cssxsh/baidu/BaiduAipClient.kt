package xyz.cssxsh.baidu

import xyz.cssxsh.baidu.oauth.*

public open class BaiduAipClient(config: BaiduAuthConfig) : AbstractAipClient(), BaiduAuthConfig by config {
    public constructor(config: BaiduAuthConfig, token: AuthorizeAccessToken) : this(config) {
        save(token = token)
    }
}