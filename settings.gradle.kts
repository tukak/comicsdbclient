import de.fayard.refreshVersions.bootstrapRefreshVersions

rootProject.buildFileName = "build.gradle.kts"
include(":app")

buildscript {
    repositories { gradlePluginPortal() }
    dependencies.classpath("de.fayard.refreshVersions:refreshVersions:0.9.7")
}

bootstrapRefreshVersions()