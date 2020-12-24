package me.dicoding.bajp.reel

import com.android.build.gradle.AppExtension
import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class ReelCustomPlugin : Plugin<Project> {

  override fun apply(project: Project) {
    //adding plugins
    project.plugins.apply("kotlin-android")
    project.plugins.apply(("kotlin-kapt"))

    //configuring android gradle extension
    val androidExtension = project.extensions.getByName("android")
    if (androidExtension is BaseExtension) {
      androidExtension.applyAndroidSettings()
      androidExtension.applyProguardSettings()
      androidExtension.enableJava8(project)
    }

    //declaring shared dependencies (default deps
    project.dependencies {
      add("implementation",Libs.AndroidX.core)
      add("implementation",Libs.AndroidX.appCompat)
      add("implementation",Libs.AndroidX.constraintLayout)
      add("implementation",Libs.Google.material)
      add("testImplementation", Libs.Testing.junit)
      add("androidTestImplementation", Libs.Testing.junitExt)
      add("androidTestImplementation", Libs.Testing.Espresso.core)
    }
  }

  private fun BaseExtension.applyAndroidSettings() {
    compileSdkVersion(30)
    buildToolsVersion = "30.0.2"
    defaultConfig {
      minSdkVersion(23)
      targetSdkVersion(30)
      versionCode = 1
      versionName = "0.0.1"
      multiDexEnabled = true

      testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
  }

  private fun BaseExtension.applyProguardSettings() {
    val proguardFilename = "proguard-rules.pro"
    when (this) {
      is LibraryExtension -> defaultConfig {
        consumerProguardFiles(proguardFilename)
      }
      is AppExtension -> buildTypes {
        getByName("release") {
          isMinifyEnabled = true
          isShrinkResources = true
          proguardFiles(
            getDefaultProguardFile("proguard-android-optimize.txt"),
              proguardFilename
          )
        }
      }
    }
  }

  private fun BaseExtension.enableJava8(project: Project) {
    compileOptions {
      isCoreLibraryDesugaringEnabled = true
      sourceCompatibility = JavaVersion.VERSION_1_8
      targetCompatibility = JavaVersion.VERSION_1_8
    }

    project.tasks.withType<KotlinCompile>().configureEach {
      kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = freeCompilerArgs + listOf(
          "-Xopt-in=kotlin.ExperimentalStdlibApi",
          "-Xopt-in=kotlin.RequiresOptIn"
        )
      }
    }

    project.dependencies {
      add("coreLibraryDesugaring", Libs.jdkDesugaring)
    }
  }
}