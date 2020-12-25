plugins {
  id ("com.android.dynamic-feature")
  id ("reel-custom-plugin")
  id("kotlin-android")
}
android {
  defaultConfig {
    applicationId ("me.dicoding.bajp.reel.favorite")
  }
  buildFeatures.viewBinding = true
}

dependencies {
  implementation(project(":core"))
  implementation(project(":app"))
  implementation(kotlin("stdlib", version = Libs.kotlin_version))
  implementation("androidx.appcompat:appcompat:1.2.0")
  implementation("androidx.constraintlayout:constraintlayout:1.1.3")

  //instrumentation testing
  androidTestImplementation(Libs.Testing.rules)
  androidTestImplementation(Libs.Testing.Espresso.contrib)
}