

allprojects {
    group = "xyz.cssxsh.baidu"
    version = "0.1.0-dev-13"
}

subprojects {
    repositories {
        mavenLocal()
        maven(url = "https://maven.aliyun.com/repository/public")
        mavenCentral()
        maven(url = "https://maven.aliyun.com/repository/gradle-plugin")
        gradlePluginPortal()
    }
}