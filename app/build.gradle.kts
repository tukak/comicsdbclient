import org.jetbrains.kotlin.gradle.internal.AndroidExtensionsExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
    /*id("kotlin-parcelize")*/
}

androidExtensions {
    configure<AndroidExtensionsExtension> {
        isExperimental = true
    }
}

android {
    compileSdkVersion(30)

    defaultConfig {
        applicationId = "cz.kutner.comicsdbclient.comicsdbclient"
        minSdkVersion(21)
        targetSdkVersion(30)
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

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }

    lintOptions {
        lintConfig = file("lint.xml")
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }

    kapt {
        javacOptions {
            // Increase the max count of errors from annotation processors.
            // Default is 100.
            option("-Xmaxerrs", 500)
        }
    }

    kotlinOptions.useIR = true
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(AndroidX.appCompat)
    implementation(AndroidX.cardView)
    implementation(AndroidX.recyclerView)
    implementation(Google.android.material)
    implementation(AndroidX.core.ktx)
    implementation(AndroidX.lifecycle.liveData)
    implementation(AndroidX.lifecycle.viewModel)
    implementation("com.github.tukak:Switcher:_")
    implementation(Square.retrofit2.retrofit)
    implementation(Square.retrofit2.converter.gson)
    implementation(KotlinX.coroutines.android)
    implementation(Square.picasso)
    implementation(Square.okHttp3.okHttp)
    implementation("com.hannesdorfmann:adapterdelegates4:_")
    implementation(Kotlin.stdlib)
    implementation("com.github.daniel-stoneuk:material-about-library:_")
    implementation( "com.mikepenz:iconics-core:_")
    implementation("com.mikepenz:material-design-iconic-typeface:_")
    implementation("io.insert-koin:koin-android:_")
    implementation("com.github.chrisbanes:PhotoView:_")
    implementation(JakeWharton.timber)
    implementation( AndroidX.constraintLayout)
    implementation("io.americanexpress.busybee:busybee-android:_")

    testImplementation(Testing.junit4)
    testImplementation(Testing.mockito.core)
    testImplementation("org.hamcrest:hamcrest-library:_")

    androidTestImplementation("io.insert-koin:koin-test:_")
    androidTestImplementation(AndroidX.test.rules)
    androidTestImplementation(AndroidX.test.runner)
    androidTestImplementation(AndroidX.test.espresso.core)
    androidTestImplementation(AndroidX.test.espresso.contrib)
}