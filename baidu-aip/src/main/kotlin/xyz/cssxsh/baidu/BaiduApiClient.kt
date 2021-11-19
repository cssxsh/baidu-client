package xyz.cssxsh.baidu


open class BaiduApiClient(config: BaiduAuthConfig) : AbstractApiClient(), BaiduAuthConfig by config {
    constructor(appId: Long, appName: String, appKey: String, secretKey: String) : this(object : BaiduAuthConfig {
        override val appName: String = appName
        override val appId: Long = appId
        override val appKey: String = appKey
        override val secretKey: String = secretKey
    })
}