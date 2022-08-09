package xyz.cssxsh.baidu.unit

import xyz.cssxsh.baidu.oauth.*

public interface BaiduUnitConfig : BaiduAuthConfig {

    /**
     * API host
     * @see HOST
     */
    public val host: String


    public companion object HOST {
        /**
         * 研发环境
         */
        public const val DEV: String = "aip.baidubce.com"

        /**
         * 生产环境-华北机房
         */
        public const val NORTH: String = "unit.bj.baidubce.com"

        /**
         * 生产环境-华东机房
         */
        public const val EAST: String = "unit.su.baidubce.com"

        /**
         * 生产环境-华南机房
         */
        public const val SOUTH: String = "unit.gz.baidubce.com"

        /**
         * 生产环境-全国域名
         */
        public const val WHOLE: String = "unit-api.baidu.com"

    }
}