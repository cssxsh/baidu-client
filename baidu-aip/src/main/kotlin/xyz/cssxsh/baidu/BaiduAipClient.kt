package xyz.cssxsh.baidu

import xyz.cssxsh.baidu.oauth.*
import java.time.OffsetDateTime

public open class BaiduAipClient(config: BaiduAuthConfig) : AbstractAipClient(), BaiduAuthConfig by config {
    public constructor(config: BaiduAuthConfig, token: AuthorizeAccessToken, time: OffsetDateTime) : this(config) {
        save(token = token, time = time)
    }
}