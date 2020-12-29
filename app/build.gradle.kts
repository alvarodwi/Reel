plugins {
    id("com.android.application")
    id("reel-custom-plugin")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    defaultConfig {
        applicationId("me.dicoding.bajp.reel")
    }
    buildFeatures {
        viewBinding = true
    }
    dynamicFeatures = mutableSetOf(":favorite")
}

dependencies {
    implementation(project(":core"))
    implementation(kotlin("stdlib", version = LibsVersion.kotlin))

    // ktx
    api(Libs.AndroidX.KTX.activity)
    api(Libs.AndroidX.KTX.fragment)

    // lifecycle
    api(Libs.AndroidX.Lifecycle.runtime)
    api(Libs.AndroidX.Lifecycle.liveData)
    api(Libs.AndroidX.Lifecycle.viewModel)

    // navigation
    api(Libs.AndroidX.Navigation.navFragment)
    api(Libs.AndroidX.Navigation.navUI)
    api(Libs.AndroidX.Navigation.dynamicFeatureFragment)

    // ui
    api(Libs.AndroidX.viewPager2)
    api(Libs.AndroidX.swipeRefresh)
    api(Libs.materialDialogs)

    // instrumentation testing
    androidTestImplementation(Libs.Testing.rules)
    androidTestImplementation(Libs.Testing.Espresso.contrib)
}
