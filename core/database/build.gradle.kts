plugins {
    alias(libs.plugins.module.android.library)
    alias(libs.plugins.module.hilt)
    alias(libs.plugins.module.room)
}

android {
    namespace = "com.skele.core.database"
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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