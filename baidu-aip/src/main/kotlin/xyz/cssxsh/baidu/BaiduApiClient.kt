package xyz.cssxsh.baidu

import xyz.cssxsh.baidu.oauth.*

open class BaiduApiClient(config: BaiduAuthConfig) : AbstractApiClient(), BaiduAuthConfig by config {
    constructor(config: BaiduAuthConfig, token: AuthorizeAccessToken) : this(config) {
        save(token)
    }
}