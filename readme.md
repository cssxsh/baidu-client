# Baidu Api Client

> 百度API的Kotlin Jvm SDK，目前实现的有 `Oauth认证` 和 `百度网盘`

## Baidu Oauth

部分SDK的前置库  
有三种[验证方式](https://developer.baidu.com/wiki/index.php?title=docs/oauth/application)

服务端的方式获取 Token, block 输入 认证网页 Url ，返回认证码  
`suspend fun authorize(block: suspend (Url) -> String)`

移动端的方式获取 Token, block 输入 认证网页 Url ，返回跳转Url  
`suspend fun implicit(block: suspend (Url) -> Url)`

设备认证的方式获取 Token, block 第一个参数是 直接网页认证的Url，第二个是 二维码认证的图片Url  
`suspend fun device(block: suspend (Url, Url) -> Unit)`

刷新 Token  
`suspend fun refresh()`

## Baidu Netdisk

构建客户端的参数需要到 [百度网盘开放中心](https://pan.baidu.com/union/apply) 申请  
客户端工作目录为`/apps/${appName}`

已完成的功能

1. 验证登录
1. 上传文件
1. 列出文件
1. 搜索文件
1. [秒传链接](https://zhuanlan.zhihu.com/p/356900770) 获取和解析

## Gradle引用包

到 [Personal Access Tokens](https://github.com/settings/tokens) 申请一个token  
然后添加repository

```
repositories {
    maven {
        url = uri("https://maven.pkg.github.com/cssxsh/baidu-client")
        credentials {
            username = "${github.id}"
            password = "${github.token}"
        }
    }
}

dependencies {
    implementation("xyz.cssxsh.baidu:baidu-netdisk:${version}")
}
```