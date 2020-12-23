object Libs {
    const val kotlin_version = "1.4.21"
    const val benmanes_version = "0.36.0"
    private const val nav_version = "2.3.2"
    private const val lifecycle_version = "2.2.0"
    private const val room_version = "2.2.6"
    private const val coroutines_version = "1.4.2"
    private const val koin_version = "2.2.2"
    private const val coil_version = "1.1.0"
    private const val okhttp_version = "4.9.0"
    private const val espresso_version = "3.3.0"
    const val materialdialogs_version = "3.3.0"

    const val androidGradlePlugin = "com.android.tools.build:gradle:4.1.1"
    const val jdkDesugaring = "com.android.tools:desugar_jdk_libs:1.0.9"

    object AndroidX {
        const val appCompat = "androidx.appcompat:appcompat:1.2.0"
        const val core = "androidx.core:core-ktx:1.3.2"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.0.4"
        const val coordinatorLayout = "androidx.coordinatorlayout:coordinatorlayout:1.1.0"
        const val swipeRefresh = "androidx.swiperefreshlayout:swiperefreshlayout:1.1.0"
        const val viewPager2 = "androidx.viewpager2:viewpager2:1.0.0"
        const val paging = "androidx.paging:paging-runtime-ktx:3.0.0-alpha11"
        // const val work = "androidx.work:work-runtime-ktx:2.4.0"

        object KTX {
            const val fragment = "androidx.fragment:fragment-ktx:1.2.5"
            const val activity = "androidx.activity:activity-ktx:1.1.0"
            const val preferences = "androidx.preference:preference-ktx:1.1.1"
            const val room = "androidx.room:room-ktx:$room_version"
        }

        object Lifecycle {
            const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version"
            const val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version"
            const val runtime = "androidx.lifecycle:lifecycle-runtime-ktx:$lifecycle_version"
            const val compiler = "androidx.lifecycle:lifecycle-compiler:$lifecycle_version"
        }

        object Navigation {
            const val navFragment = "androidx.navigation:navigation-fragment-ktx:$nav_version"
            const val navUI = "androidx.navigation:navigation-ui-ktx:$nav_version"
            const val safeArgsGradlePlugin =
                "androidx.navigation:navigation-safe-args-gradle-plugin:$nav_version"
        }

        object Room {
            const val runtime = "androidx.room:room-runtime:$room_version"
            const val compiler = "androidx.room:room-compiler:$room_version"
        }
    }

    object Google {
        const val material = "com.google.android.material:material:1.2.1"
    }

    object Kotlin {
        object Coroutines {
            const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutines_version"
            const val android =
                "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutines_version"
            const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutines_version"
        }
    }

    object Koin {
        const val core = "org.koin:koin-android:$koin_version"
        // const val scope = "org.koin:koin-android-scope:$koin_version"
        const val viewModel = "org.koin:koin-android-viewmodel:$koin_version"
        const val test = "org.koin:koin-test:$koin_version"
    }

    object CoilKt {
        const val core = "io.coil-kt:coil-base:${coil_version}"
        // const val gif = "io.coil-kt:coil-gif:${coil_version}"
    }

    object Network {
        const val retrofit = "com.squareup.retrofit2:retrofit:2.9.0"
        const val retrofitKotlinxSerializationConverter =
            "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0"
        const val kotlinxSerialization = "org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.1"

        const val okhttpLogging = "com.squareup.okhttp3:logging-interceptor:$okhttp_version"
        const val okhttpMockWebServer = "com.squareup.okhttp3:mockwebserver:$okhttp_version"
    }

    object Testing {
        const val junit = "junit:junit:4.13.1"
        const val junitExt = "androidx.test.ext:junit:1.1.2"
        const val rules = "androidx.test:rules:1.3.0"
        const val mockk = "io.mockk:mockk:1.10.3"
        const val archCore = "androidx.arch.core:core-testing:2.1.0"

        object Espresso {
            const val core = "androidx.test.espresso:espresso-core:$espresso_version"
            const val contrib = "androidx.test.espresso:espresso-contrib:$espresso_version"
            const val idlingResources =
                "androidx.test.espresso:espresso-idling-resource:$espresso_version"
        }
    }

    //other library
    const val timber = "com.jakewharton.timber:timber:4.7.1"
    object MaterialDialogs{
        const val core = "com.afollestad.material-dialogs:core:$materialdialogs_version"
    }
}