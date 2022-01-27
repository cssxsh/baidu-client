package xyz.cssxsh.baidu

import xyz.cssxsh.baidu.oauth.*

public abstract class AbstractAipClient : AipClient, AbstractBaiduAuthClient() {

    override val timeout: Long get() = AipClient.Timeout

    public override var scope: List<ScopeType> = listOf(ScopeType.PUBLIC, ScopeType.WISE_ADAPT)
        protected set
}