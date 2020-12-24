import java.util.Properties

plugins {
  id("com.android.library")
  id("reel-custom-plugin")
  kotlin("plugin.serialization") version Libs.kotlin_version
}

val localProperties = Properties()
localProperties.load(project.rootProject.file("local.properties").inputStream())

android {
  defaultConfig {
    buildConfigField("String", "tmdbApiKey", localProperties["tmdb.api.key"] as String)
  }
  testOptions {
    unitTests.isReturnDefaultValues = true
  }
}

dependencies {
  implementation(kotlin("stdlib", version = Libs.kotlin_version))

  //coroutines
  api(Libs.Kotlin.Coroutines.core)
  api(Libs.Kotlin.Coroutines.android)
  testImplementation(Libs.Kotlin.Coroutines.test)

  //lifecycle
  api(Libs.AndroidX.Lifecycle.runtime)
  api(Libs.AndroidX.Lifecycle.liveData)

  //data
  api(Libs.AndroidX.paging)
  implementation(Libs.AndroidX.Room.runtime)
  kapt(Libs.AndroidX.Room.compiler)

  //networking
  implementation(Libs.Network.kotlinxSerialization)
  implementation(Libs.Network.retrofit)
  implementation(Libs.Network.retrofitKotlinxSerializationConverter)
  implementation(Libs.Network.okhttpLogging)

  //di
  api(Libs.Koin.core)
  api(Libs.Koin.viewModel)

  //unit testing
  testImplementation(Libs.Network.okhttpMockWebServer)
}