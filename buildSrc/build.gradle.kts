plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
}

gradlePlugin {
    plugins {
        register("reel-custom-plugin"){
           id = "reel-custom-plugin"
            implementationClass = "me.dicoding.bajp.reel.ReelCustomPlugin"
        }
    }
}

repositories {
    jcenter()
    google()
    mavenCentral()
}

dependencies {
    implementation("com.android.tools.build:gradle:4.1.1")
    implementation(kotlin("gradle-plugin",version = "1.4.21"))
}