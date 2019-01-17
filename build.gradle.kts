// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {

    repositories {
        jcenter()
        google()
    }

    dependencies {
        classpath(Libs.com_android_tools_build_gradle)
        classpath(Libs.kotlin_gradle_plugin)
    }
}

plugins {
    id("de.fayard.buildSrcVersions") version Versions.de_fayard_buildsrcversions_gradle_plugin
}


allprojects {
    repositories {
        jcenter()
        google()
        maven(url = "https://jitpack.io")
        maven(url = "https://oss.sonatype.org/content/repositories/snapshots")
    }
}

//project.ext.preDexLibs = !project.hasProperty('disablePreDex')

//subprojects {
//    project.plugins.whenPluginAdded { plugin ->
//        if ("com.android.build.gradle.AppPlugin" == plugin.class.name) {
//            project.android.dexOptions.preDexLibraries = rootProject.ext.preDexLibs
//        } else if ("com.android.build.gradle.LibraryPlugin" == plugin.class.name) {
//            project.android.dexOptions.preDexLibraries = rootProject.ext.preDexLibs
//        }
//    }
//}
