// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {

    repositories {
        jcenter()
        google()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:3.4.0-beta01")
        classpath(kotlin("gradle-plugin", version = "1.3.11"))
    }
}

plugins {
    id("de.fayard.buildSrcVersions") version "0.3.2"
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
