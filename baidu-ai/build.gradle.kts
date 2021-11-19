plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
}

//setBintray(Config())

dependencies {
    api(project(":baidu-oauth"))
    implementation(ktor("client", Versions.ktor))
    implementation(ktor("client-serialization", Versions.ktor))
    implementation(ktor("client-encoding", Versions.ktor))
    implementation(ktor("client-okhttp", Versions.ktor))
    testImplementation(kotlin("test"))
}

kotlin {
    sourceSets {
        all {
            languageSettings.optIn("kotlin.RequiresOptIn")
        }
    }
    target {
        compilations.configureEach {
            kotlinOptions {
                jvmTarget = "11"
            }
        }
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

tasks {
    test {
        useJUnitPlatform()
    }
}