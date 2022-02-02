pluginManagement {
    plugins {
        kotlin("jvm") version "1.6.0"
        kotlin("plugin.serialization") version "1.6.0"
        id("net.mamoe.maven-central-publish") version "0.7.0"
    }
    repositories {
        mavenLocal()
        mavenCentral()
        gradlePluginPortal()
    }
}

rootProject.name = "baidu-client"

include("baidu-oauth")
include("baidu-netdisk")
include("baidu-translate")
include("baidu-aip")
