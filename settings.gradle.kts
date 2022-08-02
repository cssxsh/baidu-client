pluginManagement {
    plugins {
        kotlin("jvm") version "1.7.10"
        kotlin("plugin.serialization") version "1.7.10"
        id("me.him188.maven-central-publish") version "1.0.0-dev-3"
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
