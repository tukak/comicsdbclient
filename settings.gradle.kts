rootProject.buildFileName = "build.gradle.kts"
include(":app")

pluginManagement {
    repositories {
        gradlePluginPortal()
    }
}

plugins {
    // See https://jmfayard.github.io/refreshVersions
    id("de.fayard.refreshVersions") version "0.60.3"
}

refreshVersions {
    enableBuildSrcLibs()
}
