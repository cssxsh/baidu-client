allprojects {
    group = "xyz.cssxsh.baidu"
    version = "2.0.4"
}

subprojects {
    repositories {
        mavenLocal()
        maven(url = "https://maven.aliyun.com/repository/central")
        mavenCentral()
        maven(url = "https://maven.aliyun.com/repository/gradle-plugin")
        gradlePluginPortal()
    }
}