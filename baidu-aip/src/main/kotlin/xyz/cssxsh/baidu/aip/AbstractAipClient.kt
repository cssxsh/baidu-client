package xyz.cssxsh.baidu.aip

import xyz.cssxsh.baidu.oauth.*
import xyz.cssxsh.baidu.oauth.data.*

/**
 * AIP 客户端抽象实现
 */
public abstract class AbstractAipClient : AipClient, AbstractBaiduAuthClient<BaiduAuthConfig>() {

    override val timeout: Long get() = AipClient.TIMEOUT

    public override val scope: List<String>
        get() = status.scope.ifEmpty { listOf(ScopeType.PUBLIC, ScopeType.WISE_ADAPT) }
}