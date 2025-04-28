import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplicationConventionPlugin") {
            id = "skele.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidLibraryConventionPlugin") {
            id = "skele.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidApplicationComposeConventionPlugin") {
            id = "skele.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }
        register("androidLibraryComposeConventionPlugin") {
            id = "skele.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
        register("androidHiltConventionPlugin") {
            id = "skele.android.hilt"
            implementationClass = "HiltConventionPlugin"
        }
        register("androidRoomConventionPlugin") {
            id = "skele.android.room"
            implementationClass = "RoomConventionPlugin"
        }
    }
}
