@file:Suppress("UnstableApiUsage")

pluginManagement {
    plugins {
        kotlin("jvm") version "1.5.31"
        kotlin("plugin.serialization") version "1.5.31"
        id("net.mamoe.maven-central-publish") version "0.6.1"
    }
    repositories {
        mavenLocal()
        maven(url = "https://maven.aliyun.com/repository/public")
        mavenCentral()
        maven(url = "https://maven.aliyun.com/repository/gradle-plugin")
        gradlePluginPortal()
    }
}

rootProject.name = "baidu-client"

include("baidu-oauth")
include("baidu-netdisk")
include("baidu-translate")
include("baidu-aip")
