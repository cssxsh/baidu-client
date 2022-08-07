package xyz.cssxsh.baidu.unit

import xyz.cssxsh.baidu.oauth.*

public interface BaiduUnitConfig : BaiduAuthConfig {

    /**
     * API URL
     * @see Chat
     */
    public val chat: String


    public companion object Chat {
        /**
         * 研发环境
         */
        public const val DEV: String = "https://aip.baidubce.com/rpc/2.0/unit/service/v3/chat"

        /**
         * 生产环境-华北机房
         */
        public const val NORTH: String = "https://unit.bj.baidubce.com/rpc/2.0/unit/service/v3/chat"

        /**
         * 生产环境-华东机房
         */
        public const val EAST: String = "https://unit.su.baidubce.com/rpc/2.0/unit/service/v3/chat"

        /**
         * 生产环境-华南机房
         */
        public const val SOUTH: String = "https://unit.gz.baidubce.com/rpc/2.0/unit/service/v3/chat"

        /**
         * 生产环境-全国域名
         */
        public const val WHOLE: String = "https://unit-api.baidu.com/rpc/2.0/unit/service/v3/chat"

    }
}