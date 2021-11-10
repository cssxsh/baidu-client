plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
}

dependencies {
    implementation(ktor("client", Versions.ktor))
    implementation(ktor("client-serialization", Versions.ktor))
    implementation(ktor("client-encoding", Versions.ktor))
    implementation(ktor("client-okhttp", Versions.ktor))
    testImplementation(kotlin("test"))
}

kotlin {
    sourceSets {
        all {
//            languageSettings.useExperimentalAnnotation("kotlin.ExperimentalStdlibApi")
//            languageSettings.useExperimentalAnnotation("kotlinx.serialization.ExperimentalSerializationApi")
//            languageSettings.useExperimentalAnnotation("kotlinx.serialization.InternalSerializationApi")
        }
    }
}

tasks {

    test {
        useJUnitPlatform()
    }

    compileKotlin {
        kotlinOptions.freeCompilerArgs += "-Xjvm-default=all"
        kotlinOptions.jvmTarget = "11"
    }

    compileTestKotlin {
        kotlinOptions.freeCompilerArgs += "-Xjvm-default=all"
        kotlinOptions.jvmTarget = "11"
    }
}