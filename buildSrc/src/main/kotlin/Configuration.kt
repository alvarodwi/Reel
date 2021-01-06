object ProjectConfig {
    const val VERSION_CODE = 2
    const val VERSION_NAME = "1.1.0"
}

object AndroidConfig {
    const val COMPILE_SDK_VERSION = 30
    const val MIN_SDK_VERSION = 23
    const val TARGET_SDK_VERSION = 30
    const val BUILD_TOOLS_VERSION = "30.0.3"
}

object LibsVersion {
    const val kotlin = "1.4.21"
    const val versions = "0.36.0"
    const val detekt = "1.15.0"
    const val ktlint_gradle = "9.4.1"
    const val nav = "2.3.2"
    const val lifecycle = "2.2.0"
    const val room = "2.2.6"
    const val coroutines = "1.4.2"
    const val koin = "2.2.2"
    const val coil = "1.1.0"
    const val okhttp = "4.9.0"
    const val espresso = "3.3.0"
}

object Libs {
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
            const val room = "androidx.room:room-ktx:${LibsVersion.room}"
        }

        object Lifecycle {
            const val viewModel =
                "androidx.lifecycle:lifecycle-viewmodel-ktx:${LibsVersion.lifecycle}"
            const val liveData =
                "androidx.lifecycle:lifecycle-livedata-ktx:${LibsVersion.lifecycle}"
            const val runtime = "androidx.lifecycle:lifecycle-runtime-ktx:${LibsVersion.lifecycle}"
            const val compiler = "androidx.lifecycle:lifecycle-compiler:${LibsVersion.lifecycle}"
        }

        object Navigation {
            const val navFragment = "androidx.navigation:navigation-fragment-ktx:${LibsVersion.nav}"
            const val navUI = "androidx.navigation:navigation-ui-ktx:${LibsVersion.nav}"
            const val dynamicFeatureFragment =
                "androidx.navigation:navigation-dynamic-features-fragment:${LibsVersion.nav}"
            const val safeArgsGradlePlugin =
                "androidx.navigation:navigation-safe-args-gradle-plugin:${LibsVersion.nav}"
        }

        object Room {
            const val runtime = "androidx.room:room-runtime:${LibsVersion.room}"
            const val compiler = "androidx.room:room-compiler:${LibsVersion.room}"
        }
    }

    object Google {
        const val material = "com.google.android.material:material:1.2.1"
    }

    object Kotlin {
        object Coroutines {
            const val core =
                "org.jetbrains.kotlinx:kotlinx-coroutines-core:${LibsVersion.coroutines}"
            const val android =
                "org.jetbrains.kotlinx:kotlinx-coroutines-android:${LibsVersion.coroutines}"
            const val test =
                "org.jetbrains.kotlinx:kotlinx-coroutines-test:${LibsVersion.coroutines}"
        }
    }

    object Koin {
        const val core = "org.koin:koin-android:${LibsVersion.koin}"
        // const val scope = "org.koin:koin-android-scope:$LibsVersion.koin"
        const val viewModel = "org.koin:koin-android-viewmodel:${LibsVersion.koin}"
        const val test = "org.koin:koin-test:${LibsVersion.koin}"
    }

    object CoilKt {
        const val core = "io.coil-kt:coil-base:${LibsVersion.coil}"
        // const val gif = "io.coil-kt:coil-gif:${LibsVersion.coil"
    }

    object Square {
        const val retrofit = "com.squareup.retrofit2:retrofit:2.9.0"
        const val okhttpLogging = "com.squareup.okhttp3:logging-interceptor:${LibsVersion.okhttp}"
        const val okhttpMockWebServer = "com.squareup.okhttp3:mockwebserver:${LibsVersion.okhttp}"
        const val leakCanary = "com.squareup.leakcanary:leakcanary-android:2.6"
    }

    object Network {
        const val retrofitKotlinxSerializationConverter =
            "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0"
        const val kotlinxSerialization = "org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.1"
    }

    object Testing {
        const val junit = "junit:junit:4.13.1"
        const val junitExt = "androidx.test.ext:junit:1.1.2"
        const val rules = "androidx.test:rules:1.3.0"
        const val mockk = "io.mockk:mockk:1.10.3-jdk8"
        const val archCore = "androidx.arch.core:core-testing:2.1.0"

        object Espresso {
            const val core = "androidx.test.espresso:espresso-core:${LibsVersion.espresso}"
            const val contrib = "androidx.test.espresso:espresso-contrib:${LibsVersion.espresso}"
            const val idlingResources =
                "androidx.test.espresso:espresso-idling-resource:${LibsVersion.espresso}"
        }
    }

    // other library
    const val timber = "com.jakewharton.timber:timber:4.7.1"
    const val materialDialogs = "com.afollestad.material-dialogs:core:3.3.0"
    const val lottie = "com.airbnb.android:lottie:3.6.0"
    const val bindingExtension = "com.github.jintin:BindingExtension:3.0.0"
}
