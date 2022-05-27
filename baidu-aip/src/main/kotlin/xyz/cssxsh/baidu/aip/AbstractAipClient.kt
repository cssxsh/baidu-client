package xyz.cssxsh.baidu.aip

import xyz.cssxsh.baidu.oauth.*
import xyz.cssxsh.baidu.oauth.data.*

public abstract class AbstractAipClient : AipClient, AbstractBaiduAuthClient<BaiduAuthConfig>() {

    override val timeout: Long get() = AipClient.TIMEOUT

    public override val scope: List<String>
        get() = status.scope.ifEmpty { listOf(ScopeType.PUBLIC, ScopeType.WISE_ADAPT) }
}