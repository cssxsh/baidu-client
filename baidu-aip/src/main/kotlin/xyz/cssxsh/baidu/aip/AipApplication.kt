package xyz.cssxsh.baidu.aip

/**
 * AIP 应用基本接口
 */
public sealed interface AipApplication {
    public val client: AipClient
}