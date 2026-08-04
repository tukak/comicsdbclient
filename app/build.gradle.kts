plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
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
    compileSdk = 37

    defaultConfig {
        applicationId = "cz.kutner.comicsdbclient.comicsdbclient"
        minSdk = 24
        targetSdk = 37
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

buildFeatures {
        buildConfig = true
        compose = true
    }

    kotlin {
        compilerOptions {
            freeCompilerArgs.addAll(
                "-opt-in=androidx.compose.material3.ExperimentalMaterial3ExpressiveApi"
            )
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.viewmodel)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.kotlinx.serialization)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.coroutines.android)
    implementation(libs.coil)
    implementation(libs.coil.network)
    implementation(libs.coil.compose)
    implementation(libs.okhttp)
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)
    implementation(libs.telephoto)
    implementation(libs.profileinstaller)
    implementation(libs.timber)

    implementation(platform(libs.compose.bom))
    implementation(libs.compose.material3)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.activity.compose)
    implementation(libs.navigation.compose)
    implementation(libs.compose.material.icons.extended)
    debugImplementation(libs.compose.ui.tooling)

    testImplementation(libs.junit)
    testImplementation(libs.mockito)
    testImplementation(libs.hamcrest)
    testImplementation(libs.coroutines.test)

    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.compose.ui.test.junit4)
    debugImplementation(libs.compose.ui.test.manifest)
    androidTestImplementation(libs.koin.test)
    androidTestImplementation(libs.androidx.test.core)
    androidTestImplementation(libs.androidx.test.rules)
    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)
}
