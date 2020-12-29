package me.dicoding.bajp.reel

import AndroidConfig
import Libs
import ProjectConfig
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
        // adding plugins
        project.plugins.apply("kotlin-android")
        project.plugins.apply("kotlin-kapt")

        // configuring android gradle extension
        val androidExtension = project.extensions.getByName("android")
        if (androidExtension is BaseExtension) {
            androidExtension.applyAndroidSettings()
            if (!project.plugins.hasPlugin("com.android.dynamic-feature")) {
                androidExtension.applyProguardSettings()
            }
            androidExtension.enableJava8(project)
        }

        // declaring shared dependencies (default android dependencies + timber + mockk)
        project.dependencies {
            add("implementation", Libs.AndroidX.core)
            add("implementation", Libs.AndroidX.appCompat)
            add("implementation", Libs.AndroidX.constraintLayout)
            add("implementation", Libs.AndroidX.coordinatorLayout)
            add("implementation", Libs.Google.material)
            add("implementation", Libs.timber)
            add("testImplementation", Libs.Testing.junit)
            add("testImplementation", Libs.Testing.mockk)
            add("testImplementation", Libs.Testing.archCore)
            add("androidTestImplementation", Libs.Testing.junitExt)
            add("androidTestImplementation", Libs.Testing.Espresso.core)
        }
    }

    private fun BaseExtension.applyAndroidSettings() {
        compileSdkVersion(AndroidConfig.COMPILE_SDK_VERSION)
        buildToolsVersion = AndroidConfig.BUILD_TOOLS_VERSION
        defaultConfig {
            minSdkVersion(AndroidConfig.MIN_SDK_VERSION)
            targetSdkVersion(AndroidConfig.TARGET_SDK_VERSION)
            versionCode = ProjectConfig.VERSION_CODE
            versionName = ProjectConfig.VERSION_NAME

            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }
        testOptions {
            unitTests.isReturnDefaultValues = true
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
