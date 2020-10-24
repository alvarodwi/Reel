object Libs {
    const val kotlin_version = "1.4.10"
    const val benmanes_version = "0.33.0"
    private const val nav_version = "2.3.1"
    private const val lifecycle_version = "2.2.0"
    private const val coroutines_version = "1.3.9"
    private const val koin_version = "2.1.6"
    private const val coil_version = "0.13.0"

    const val androidGradlePlugin = "com.android.tools.build:gradle:4.1.0"
    const val jdkDesugaring = "com.android.tools:desugar_jdk_libs:1.0.9"

    object AndroidX {
        const val appCompat = "androidx.appcompat:appcompat:1.2.0"
        const val core = "androidx.core:core-ktx:1.3.2"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.0.2"
        const val coordinatorLayout = "androidx.coordinatorlayout:coordinatorlayout:1.1.0"
        const val swipeRefresh = "androidx.swiperefreshlayout:swiperefreshlayout:1.1.0"
        const val viewPager2 = "androidx.viewpager2:viewpager2:1.0.0"
        const val paging = "androidx.paging:paging-runtime-ktx:2.1.2"
        const val work = "androidx.work:work-runtime-ktx:2.4.0"

        object KTX {
            const val fragment = "androidx.fragment:fragment-ktx:1.2.5"
            const val activity = "androidx.activity:activity-ktx:1.1.0"
            const val preferences = "androidx.preference:preference-ktx:1.1.1"
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
    }

    object Google {
        const val material = "com.google.android.material:material:1.2.1"
    }

    object Kotlin {
        object Coroutines {
            const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${coroutines_version}"
            const val android =
                "org.jetbrains.kotlinx:kotlinx-coroutines-android:${coroutines_version}"
        }
    }

    object Koin {
        const val core = "org.koin:koin-android:$koin_version"
        const val scope = "org.koin:koin-android-scope:$koin_version"
        const val viewModel = "org.koin:koin-android-viewmodel:$koin_version"
        const val ext = "org.koin:koin-android-ext:$koin_version"
    }

    object CoilKt {
        const val core = "io.coil-kt:coil-base:${coil_version}"
        const val gif = "io.coil-kt:coil-gif:${coil_version}"
    }

    object Testing {
        const val junit = "junit:junit:4.13.1"
        const val junitExt = "androidx.test.ext:junit:1.1.2"
        const val espresso = "androidx.test.espresso:espresso-core:3.3.0"
    }

    //other library
    const val retrofit = "com.squareup.retrofit2:retrofit:2.9.0"
    const val retrofitKotlinxSerializationConverter =
        "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.7.0"
    const val okhttpLogging = "com.squareup.okhttp3:logging-interceptor:4.9.0"
    const val timber = "com.jakewharton.timber:timber:4.7.1"
    const val kotlinxSerialization = "org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.0"
}