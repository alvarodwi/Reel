plugins {
    id("com.android.dynamic-feature")
    id("reel-custom-plugin")
    id("kotlin-android")
}
android {
    defaultConfig {
        applicationId("me.dicoding.bajp.reel.favorite")
    }
    buildFeatures.viewBinding = true
}

dependencies {
    implementation(project(":core"))
    implementation(project(":app"))
    implementation(kotlin("stdlib", version = LibsVersion.kotlin))

    // instrumentation testing
    androidTestImplementation(Libs.Testing.rules)
    androidTestImplementation(Libs.Testing.Espresso.contrib)
}
