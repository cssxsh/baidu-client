package xyz.cssxsh.baidu.aip

import xyz.cssxsh.baidu.oauth.*

/**
 * Baidu AI 客户端，一个客户端可以支持多个应用
 * @param config 客户端的配置
 * @see status
 * @see AipContentCensor
 * @see AipNaturalLanguageProcessing
 * @see AipTextToSpeech
 * @see AipTranslator
 */
public open class BaiduAipClient(override val config: BaiduAuthConfig) : AbstractAipClient() {
    public constructor(config: BaiduAuthConfig, token: AuthorizeAccessToken) : this(config) {
        save(token = token)
    }
}