package xyz.cssxsh.baidu

public interface NetDiskClient : BaiduUserAuthClient {
    public val appDataFolder: String

    public companion object {
        public const val Timeout: Long = 30 * 1000L
    }
}