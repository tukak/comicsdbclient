import kotlin.String

/**
 * Find which updates are available by running
 *     `$ ./gradlew buildSrcVersions`
 * This will only update the comments.
 *
 * YOU are responsible for updating manually the dependency version. */
object Versions {
    const val appcompat: String = "1.0.2" 

    const val cardview: String = "1.0.0" 

    const val constraintlayout: String = "2.0.0-alpha3" 

    const val core_ktx: String = "1.0.1" 

    const val androidx_databinding: String = "3.4.0-beta01" 

    const val androidx_lifecycle: String = "2.0.0" 

    const val recyclerview: String = "1.0.0" 

    const val androidx_test_espresso: String = "3.1.1" 

    const val androidx_test: String = "1.1.1" 

    const val com_android_tools_build_gradle: String = "3.4.0-beta01" 

    const val lint_gradle: String = "26.4.0-beta01" 

    const val photoview: String = "2.3.0" 

    const val material_about_library: String = "2.4.2" 

    const val switcher: String = "2.4.0" 

    const val material: String = "1.0.0" 

    const val adapterdelegates4: String = "4.0.0" 

    const val okhttp3_idling_resource: String = "1.0.1-SNAPSHOT" 

    const val retrofit2_kotlin_coroutines_adapter: String = "0.9.2" 

    const val timber: String = "4.7.1" 

    const val iconics_core: String = "3.1.0" 

    const val material_design_iconic_typeface: String = "2.2.0.5" 

    const val okhttp: String = "3.12.1" 

    const val picasso: String = "2.71828" 

    const val com_squareup_retrofit2: String = "2.5.0" 

    const val de_fayard_buildsrcversions_gradle_plugin: String = "0.3.2" 

    const val junit: String = "4.12" 

    const val hamcrest_library: String = "2.1" 

    const val org_jetbrains_kotlin: String = "1.3.20"

    const val kotlinx_coroutines_android: String = "1.1.0" 

    const val org_koin: String = "1.0.2" 

    const val mockito_core: String = "2.23.4" 

    /**
     *
     *   To update Gradle, edit the wrapper file at path:
     *      ./gradle/wrapper/gradle-wrapper.properties
     */
    object Gradle {
        const val runningVersion: String = "5.1.1"

        const val currentVersion: String = "5.1.1"

        const val nightlyVersion: String = "5.3-20190124000313+0000"

        const val releaseCandidate: String = ""
    }
}
