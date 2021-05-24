package xyz.cssxsh.bintray

import org.gradle.api.Plugin
import org.gradle.api.Project

class BintrayExtensionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        val config = target.extensions.create("cssxsh", Config::class.java)
        target.setBintray(config)
    }
}