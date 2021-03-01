package xyz.cssxsh.baidu.oauth

/**
 * [wiki](http://developer.baidu.com/wiki/index.php?title=docs/oauth/authorization)
 */
enum class DisplayType(val value: String) {
    /**
     * 全屏形式的授权页面(默认)，适用于web应用。
     */
    PAGE(value = "page"),

    /**
     * 弹框形式的授权页面，适用于桌面软件应用和web应用。
     */
    POPUP(value = "popup"),

    /**
     * 浮层形式的授权页面，只能用于站内web应用。
     */
    DIALOG(value = "dialog"),

    /**
     * Iphone/Android等智能移动终端上用的授权页面，适用于Iphone/Android等智能移动终端上的应用。
     */
    MOBILE(value = "mobile"),

    /**
     * 电视等超大显示屏使用的授权页面
     */
    TV(value = "tv"),

    /**
     * IPad/Android等智能平板电脑使用的授权页面。
     */
    PAD(value = "pad");

    override fun toString(): String = value
}