plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
}

dependencies {
    implementation("io.ktor:ktor-client:1.6.5")
    implementation("io.ktor:ktor-client-serialization:1.6.5")
    implementation("io.ktor:ktor-client-encoding:1.6.5")
    implementation("io.ktor:ktor-client-okhttp:1.6.5")
    testImplementation(kotlin("test"))
}

kotlin {
    explicitApi()
    sourceSets {
        all {
            languageSettings.optIn("kotlin.RequiresOptIn")
        }
    }
    target.compilations {
        all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks {
    test {
        useJUnitPlatform()
    }
}