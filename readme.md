# Baidu Api Client

> 百度API的Kotlin Jvm SDK

![baidu-oauth](https://img.shields.io/maven-central/v/xyz.cssxsh.baidu/baidu-oauth)
![baidu-aip](https://img.shields.io/maven-central/v/xyz.cssxsh.baidu/baidu-aip)
![baidu-netdisk](https://img.shields.io/maven-central/v/xyz.cssxsh.baidu/baidu-netdisk)

## Baidu User Oauth

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
2. 上传文件
3. 列出文件
4. 搜索文件
5. [秒传链接](https://zhuanlan.zhihu.com/p/356900770) 获取和解析

## Baidu AIP

[AIP文档](https://ai.baidu.com/ai-doc)

目前实现的功能有

* AipContentCensor 智能内容审核

## Gradle引用包

```
repositories {
    mavenCentral()
}

dependencies {
    implementation("xyz.cssxsh.baidu:baidu-netdisk:${version}")
    implementation("xyz.cssxsh.baidu:baidu-oauth:${version}")
}

java {
    disableAutoTargetJvm()
}
```