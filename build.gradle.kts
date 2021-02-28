

allprojects {
    group = "xyz.cssxsh.baidu"
    version = "0.1.0-dev-1"
}

subprojects {
    repositories {
        mavenLocal()
        maven(url = "https://maven.aliyun.com/repository/releases")
        maven(url = "https://mirrors.huaweicloud.com/repository/maven")
        // bintray dl.bintray.com -> bintray.proxy.ustclug.org
        maven(url = "https://bintray.proxy.ustclug.org/him188moe/mirai/")
        maven(url = "https://bintray.proxy.ustclug.org/kotlin/kotlin-dev")
        maven(url = "https://bintray.proxy.ustclug.org/kotlin/kotlinx/")
        // central
        maven(url = "https://maven.aliyun.com/repository/central")
        mavenCentral()
        // jcenter
        maven(url = "https://maven.aliyun.com/repository/jcenter")
        jcenter()
        gradlePluginPortal()
    }
}