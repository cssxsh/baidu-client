package xyz.cssxsh.baidu.aip

import xyz.cssxsh.baidu.oauth.*
import xyz.cssxsh.baidu.oauth.data.ScopeType

public abstract class AbstractAipClient : AipClient, AbstractBaiduAuthClient<BaiduAuthConfig>() {

    override val timeout: Long get() = AipClient.Timeout

    public override val scope: List<String>
        get() = status.scope.ifEmpty { listOf(ScopeType.PUBLIC, ScopeType.WISE_ADAPT) }
}