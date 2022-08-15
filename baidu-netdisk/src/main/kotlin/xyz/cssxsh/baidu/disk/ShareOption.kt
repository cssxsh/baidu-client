package xyz.cssxsh.baidu.disk


/**
 * 分享选项
 * @param period 过期类型，0 永不过期，1 一天后过期，7 七天后过期，30 三十天过期
 * @param channel 分享类型 4 私密链接
 * @param easy 免密码
 */
public data class ShareOption(
    val period: Int,
    val channel: Int,
    val easy: Boolean
)