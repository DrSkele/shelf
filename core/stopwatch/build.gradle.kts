plugins {
    alias(libs.plugins.module.android.library)
    alias(libs.plugins.module.hilt)
}

android {
    namespace = "com.skele.core.stopwatch"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
}

dependencies {
    api(project(":core:common"))
    testImplementation(libs.kotlinx.coroutines.test)
}
