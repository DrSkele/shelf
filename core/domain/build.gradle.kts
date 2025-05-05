plugins {
    alias(libs.plugins.module.android.library)
    alias(libs.plugins.module.hilt)
}

android {
    namespace = "com.skele.core.domain"
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
    implementation(project(":core:data"))
    implementation(project(":core:timer"))
    api(project(":core:model"))
}
