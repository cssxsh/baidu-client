package xyz.cssxsh.baidu.aip

import xyz.cssxsh.baidu.oauth.*


public open class BaiduAipClient(public override val config: BaiduAuthConfig) : AbstractAipClient() {
    public constructor(config: BaiduAuthConfig, token: AuthorizeAccessToken) : this(config) {
        save(token = token)
    }
}