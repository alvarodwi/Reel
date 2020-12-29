import java.util.Properties

plugins {
    id("com.android.library")
    id("reel-custom-plugin")
    kotlin("kapt")
    kotlin("plugin.serialization") version LibsVersion.kotlin
}

val localProperties = Properties()
localProperties.load(project.rootProject.file("local.properties").inputStream())

android {
    defaultConfig {
        buildConfigField("String", "tmdbApiKey", localProperties["tmdb.api.key"] as String)
    }
}

dependencies {
    implementation(kotlin("stdlib", version = LibsVersion.kotlin))

    // coroutines
    api(Libs.Kotlin.Coroutines.core)
    api(Libs.Kotlin.Coroutines.android)
    testImplementation(Libs.Kotlin.Coroutines.test)

    // ui
    api(Libs.AndroidX.paging)
    api(Libs.CoilKt.core)

    // data
    implementation(Libs.AndroidX.Room.runtime)
    implementation(Libs.AndroidX.KTX.room)
    kapt(Libs.AndroidX.Room.compiler)
    api(Libs.AndroidX.KTX.preferences)

    // networking
    implementation(Libs.Network.kotlinxSerialization)
    implementation(Libs.Network.retrofit)
    implementation(Libs.Network.retrofitKotlinxSerializationConverter)
    implementation(Libs.Network.okhttpLogging)

    // di
    api(Libs.Koin.core)
    api(Libs.Koin.viewModel)

    // unit testing + espresso idling resource
    testImplementation(Libs.Network.okhttpMockWebServer)
    api(Libs.Testing.Espresso.idlingResources)
}
