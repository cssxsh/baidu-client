package xyz.cssxsh.baidu.aip

/**
 * AIP 应用基本接口
 * @property client AIP 客户端
 */
public sealed interface AipApplication {
    public val client: AipClient
}