plugins {
  id("com.android.application")
  id("reel-custom-plugin")
  id("androidx.navigation.safeargs.kotlin")
  id("java-test-fixtures")
}

android {
  defaultConfig {
    applicationId("me.dicoding.bajp.reel")
  }
  buildFeatures {
    viewBinding = true
  }
}

dependencies {
  //core
  implementation(project(":core"))
  testImplementation(testFixtures(project(":core")))
  implementation(kotlin("stdlib", version = Libs.kotlin_version))

  //ktx
  api(Libs.AndroidX.KTX.activity)
  api(Libs.AndroidX.KTX.fragment)

  //lifecycle
  api(Libs.AndroidX.Lifecycle.runtime)
  api(Libs.AndroidX.Lifecycle.liveData)
  api(Libs.AndroidX.Lifecycle.viewModel)

  //navigation
  api(Libs.AndroidX.Navigation.navFragment)
  api(Libs.AndroidX.Navigation.navUI)

  //ui
  api(Libs.AndroidX.viewPager2)
  api(Libs.AndroidX.swipeRefresh)
  api(Libs.MaterialDialogs.core)

  //instrumentation testing
  androidTestImplementation(Libs.Testing.rules)
  androidTestImplementation(Libs.Testing.Espresso.contrib)
}