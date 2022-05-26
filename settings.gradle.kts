pluginManagement {
    plugins {
        kotlin("jvm") version "1.6.21"
        kotlin("plugin.serialization") version "1.6.21"
        id("net.mamoe.maven-central-publish") version "0.7.1"
    }
    repositories {
        mavenLocal()
        mavenCentral()
        gradlePluginPortal()
    }
}

rootProject.name = "baidu-client"

include("baidu-api")
include("baidu-oauth")
include("baidu-netdisk")
include("baidu-aip")
