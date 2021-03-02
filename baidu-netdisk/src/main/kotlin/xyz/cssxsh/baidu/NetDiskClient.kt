package xyz.cssxsh.baidu

interface NetDiskClient: BaiduAuthClient {
    val appDataFolder: String
}