

allprojects {
    group = "xyz.cssxsh.baidu"
    version = "0.1.0-dev-11"
}

subprojects {
    repositories {
        mavenLocal()
        maven(url = "https://maven.aliyun.com/repository/releases")
        maven(url = "https://maven.aliyun.com/repository/public")
        mavenCentral()
        jcenter()
        maven(url = "https://maven.aliyun.com/repository/gradle-plugin")
        gradlePluginPortal()
    }
}