plugins {
    alias(libs.plugins.module.android.application.compose)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.skele.practice"

    defaultConfig {
        applicationId = "com.skele.practice"
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {
    implementation(libs.kotlinx.serialization.core)
    implementation(libs.kotlinx.serialization.json)

    implementation("com.google.code.gson:gson:2.10.1")
}