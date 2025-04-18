plugins {
    alias(libs.plugins.module.android.application.compose)
    alias(libs.plugins.module.hilt)
}

android {
    namespace = "com.skele.pomodoro"

    defaultConfig {
        applicationId = "com.skele.pomodoro"
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
    implementation(libs.androidx.datastore.preferences)
    testImplementation(libs.kotlinx.coroutines.test)
}