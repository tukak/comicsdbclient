import org.jetbrains.kotlin.gradle.internal.AndroidExtensionsExtension

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
}

androidExtensions {
    configure<AndroidExtensionsExtension> {
        isExperimental = true
    }
}

android {
    compileSdkVersion("android-Q")

    defaultConfig {
        applicationId = "cz.kutner.comicsdbclient.comicsdbclient"
        minSdkVersion(21)
        targetSdkVersion("Q")
        versionCode = Tools.getVersionCode()
        versionName = Tools.getVersionName()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
   /*    DdmPreferences.setTimeOut(60000)*/
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            isZipAlignEnabled = true
        }
    }

    sourceSets {
        getByName("main").apply {
            java.srcDirs("src/main/kotlin")
        }
        getByName("test").apply {
            java.srcDirs("src/test/kotlin")
        }
        getByName("androidTest").apply {
            java.srcDirs("src/androidTest/kotlin")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    lintOptions {
        setLintConfig(file("lint.xml"))
    }

    dataBinding {
        isEnabled = true
    }

    kapt {
        javacOptions {
            // Increase the max count of errors from annotation processors.
            // Default is 100.
            option("-Xmaxerrs", 500)
        }
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Libs.appcompat)
    implementation(Libs.cardview)
    implementation(Libs.recyclerview)
    implementation(Libs.material)
    implementation(Libs.core_ktx)
    implementation(Libs.lifecycle_livedata)
    implementation(Libs.lifecycle_viewmodel)
    implementation(Libs.switcher)
    implementation(Libs.retrofit)
    implementation(Libs.converter_gson)
    implementation(Libs.kotlinx_coroutines_android)
    implementation(Libs.retrofit2_kotlin_coroutines_adapter)
    implementation(Libs.picasso)
    implementation(Libs.okhttp)
    implementation(Libs.adapterdelegates4)
    implementation(Libs.kotlin_stdlib)
    implementation(Libs.material_about_library)
    implementation(Libs.iconics_core)
    implementation(Libs.material_design_iconic_typeface)
    implementation(Libs.koin_android)
    implementation(Libs.koin_androidx_viewmodel)
    implementation(Libs.photoview)
    implementation(Libs.timber)
    implementation(Libs.constraintlayout)

    testImplementation(Libs.junit)
    testImplementation(Libs.mockito_core)
    testImplementation(Libs.hamcrest_library)

    androidTestImplementation(Libs.koin_test)
    androidTestImplementation(Libs.androidx_test_rules)
    androidTestImplementation(Libs.androidx_test_runner)
    androidTestImplementation(Libs.espresso_core)
    androidTestImplementation(Libs.espresso_contrib)
    androidTestImplementation(Libs.okhttp3_idling_resource)

}