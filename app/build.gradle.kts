plugins {
    id ("com.android.application")
    kotlin("android")
    kotlin("kapt")
    kotlin("plugin.serialization")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    compileSdkVersion(30)
    buildToolsVersion = "30.0.2"

    defaultConfig {
        applicationId("me.dicoding.bajp.reel")
        minSdkVersion(23)
        targetSdkVersion(30)
        versionCode = 1
        versionName = "0.0.1"
        multiDexEnabled = true

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isShrinkResources = true
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        getByName("debug") {
            applicationIdSuffix = ".debug"
            isMinifyEnabled = false
        }
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = freeCompilerArgs + listOf(
            "-Xopt-in=kotlin.ExperimentalStdlibApi",
            "-Xopt-in=kotlin.RequiresOptIn"
        )
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    coreLibraryDesugaring(Libs.jdkDesugaring)
    //core
    implementation(kotlin("stdlib", version = Libs.kotlin_version))
    implementation(Libs.AndroidX.core)
    implementation(Libs.AndroidX.appCompat)
    implementation(Libs.AndroidX.constraintLayout)
    implementation(Libs.AndroidX.coordinatorLayout)

    //coroutines
    implementation(Libs.Kotlin.Coroutines.core)
    implementation(Libs.Kotlin.Coroutines.android)

    //ktx
    implementation(Libs.AndroidX.KTX.activity)
    implementation(Libs.AndroidX.KTX.fragment)
    implementation(Libs.AndroidX.KTX.preferences)

    //lifecycle
    implementation(Libs.AndroidX.Lifecycle.runtime)
    implementation(Libs.AndroidX.Lifecycle.liveData)
    implementation(Libs.AndroidX.Lifecycle.viewModel)
    kapt(Libs.AndroidX.Lifecycle.compiler)

    //data
    implementation(Libs.kotlinxSerialization)

    //ui
    implementation(Libs.Google.material)
    implementation(Libs.AndroidX.viewPager2)
    implementation(Libs.AndroidX.paging)
    implementation(Libs.AndroidX.swipeRefresh)
    implementation(Libs.CoilKt.core)

    //di
    implementation(Libs.Koin.core)
    implementation(Libs.Koin.scope)
    implementation(Libs.Koin.viewModel)

    //logging
    implementation(Libs.timber)

    //testing
    testImplementation(Libs.Testing.junit)
    androidTestImplementation(Libs.Testing.junitExt)
    androidTestImplementation(Libs.Testing.espresso)
}