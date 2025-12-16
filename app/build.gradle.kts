plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.kotlin.parcelize)
}

fun getVersionCode(): Int {
    val result = providers.exec {
        commandLine("git", "rev-list", "--first-parent", "--count", "origin/master")
    }.standardOutput.asText.get().trim()
    return result.toInt() * 100 + 4000000
}

fun getVersionName(): String = providers.exec {
    commandLine("git", "describe", "--tags", "--dirty")
}.standardOutput.asText.get().trim()

android {
    namespace = "cz.kutner.comicsdb"
    compileSdk = 36

    defaultConfig {
        applicationId = "cz.kutner.comicsdbclient.comicsdbclient"
        minSdk = 24
        targetSdk = 36
        versionCode = getVersionCode()
        versionName = getVersionName()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    lint {
        lintConfig = file("lint.xml")
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
        buildConfig = true
    }

    kapt {
        javacOptions {
            option("-Xmaxerrs", "500")
        }
    }
}

kotlin {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
    }
}

dependencies {
    implementation(libs.kotlin.stdlib)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.recyclerview)
    implementation(libs.material)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.livedata)
    implementation(libs.androidx.lifecycle.viewmodel)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.coroutines.android)
    implementation(libs.coil)
    implementation(libs.coil.network)
    implementation(libs.okhttp)
    implementation(libs.adapterdelegates)
    implementation(libs.material.about)
    implementation(libs.iconics.core)
    implementation(libs.iconics.material.typeface)
    implementation(libs.koin.android)
    implementation(libs.photoview)
    implementation(libs.timber)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.busybee)

    testImplementation(libs.junit)
    testImplementation(libs.mockito)
    testImplementation(libs.hamcrest)

    androidTestImplementation(libs.koin.test)
    androidTestImplementation(libs.androidx.test.core)
    androidTestImplementation(libs.androidx.test.rules)
    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.espresso.contrib)
}
