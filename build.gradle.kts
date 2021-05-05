// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {

    repositories {
        jcenter()
        google()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:_")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:_")

    }
}

allprojects {
    repositories {
        jcenter()
        google()
        maven(url = "https://jitpack.io")
        //maven(url = "https://dl.bintray.com/kotlin/kotlin-eap")
    }
}