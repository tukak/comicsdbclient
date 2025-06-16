rootProject.buildFileName = "build.gradle.kts"
include(":app")

pluginManagement {
    repositories {
        gradlePluginPortal()
    }
}

plugins {
    // See https://jmfayard.github.io/refreshVersions
    id("de.fayard.refreshVersions") version "0.51.0"
////                            # available:"0.60.0"
////                            # available:"0.60.1"
////                            # available:"0.60.2"
////                            # available:"0.60.3"
////                            # available:"0.60.4"
////                            # available:"0.60.5"
}

refreshVersions {
    enableBuildSrcLibs()
}
