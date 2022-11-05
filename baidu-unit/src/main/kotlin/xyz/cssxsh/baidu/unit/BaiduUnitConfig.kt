package xyz.cssxsh.baidu.unit

import xyz.cssxsh.baidu.oauth.*

/**
 * Unit 配置信息
 * @property host API host [HOST]
 */
public interface BaiduUnitConfig : BaiduAuthConfig {

    public val host: String


    /**
     * 可配置的域名
     * @property DEV 研发环境
     * @property NORTH 生产环境-华北机房
     * @property EAST 生产环境-华东机房
     * @property SOUTH 生产环境-华南机房
     * @property WHOLE 生产环境-全国域名
     */
    public companion object HOST {
        public const val DEV: String = "aip.baidubce.com"
        public const val NORTH: String = "unit.bj.baidubce.com"
        public const val EAST: String = "unit.su.baidubce.com"
        public const val SOUTH: String = "unit.gz.baidubce.com"
        public const val WHOLE: String = "unit-api.baidu.com"

    }
}