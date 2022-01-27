@file:Suppress("UnstableApiUsage")

pluginManagement {
    plugins {
        kotlin("jvm") version "1.6.0"
        kotlin("plugin.serialization") version "1.6.0"
        id("net.mamoe.maven-central-publish") version "0.7.0"
    }
    repositories {
        mavenLocal()
        maven(url = "https://maven.aliyun.com/repository/central")
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
