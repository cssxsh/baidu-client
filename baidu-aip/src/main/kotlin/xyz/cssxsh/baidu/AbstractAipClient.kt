package xyz.cssxsh.baidu

import xyz.cssxsh.baidu.oauth.*

/**
 * TODO: openapi.baidu.com -> aip.baidubce.com
 */
abstract class AbstractAipClient : AipClient, AbstractBaiduAuthClient() {

    override val timeout: Long get() = AipClient.Timeout

    override var scope = listOf(ScopeType.PUBLIC, ScopeType.WISE_ADAPT)
        protected set
}