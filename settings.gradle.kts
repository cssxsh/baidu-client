@file:Suppress("UnstableApiUsage")

pluginManagement {
    plugins {
        kotlin("jvm") version "1.4.30"
        kotlin("plugin.serialization") version  "1.4.30"
        `maven-publish`
        id("com.jfrog.bintray") version  "1.8.5"
    }
    repositories {
        mavenLocal()
        maven(url = "https://maven.aliyun.com/repository/central")
        maven(url = "https://maven.aliyun.com/repository/jcenter")
        maven(url = "https://maven.aliyun.com/repository/gradle-plugin")
        mavenCentral()
        jcenter()
        gradlePluginPortal()
    }
}

rootProject.name = "baidu-client"

include("baidu-oauth")
include("baidu-netdisk")
include("baidu-translate")
include("baidu-ai")
