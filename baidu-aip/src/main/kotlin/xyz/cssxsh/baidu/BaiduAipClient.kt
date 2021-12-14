package xyz.cssxsh.baidu

import xyz.cssxsh.baidu.oauth.*
import java.time.OffsetDateTime

open class BaiduAipClient(config: BaiduAuthConfig) : AbstractAipClient(), BaiduAuthConfig by config {
    constructor(config: BaiduAuthConfig, token: AuthorizeAccessToken, time: OffsetDateTime) : this(config) {
        save(token = token, time = time)
    }
}