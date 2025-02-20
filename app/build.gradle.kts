plugins {
    alias(libs.plugins.module.android.application.compose)
}

android {
    namespace = "com.skele.myapp"
    defaultConfig {
        applicationId = "com.skele.myapp"

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

}