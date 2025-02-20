package com.skele.build_logic.convention.extension

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureAndroidCompose(commonExtension: CommonExtension<*, *, *, *, *, *>) {
    commonExtension.apply {
        apply(plugin = "org.jetbrains.kotlin.plugin.compose")
        apply(plugin = "org.jetbrains.kotlin.plugin.serialization")

        buildFeatures {
            compose = true
        }
        composeOptions {
            kotlinCompilerExtensionVersion = "1.5.1"
        }

        dependencies {
            val bom = libs.findLibrary("androidx.compose.bom").get()
            add("implementation", platform(bom))
            add("androidTestImplementation", platform(bom))
            add("implementation", libs.findLibrary("androidx.lifecycle.runtime.ktx").get())
            add("implementation", libs.findLibrary("androidx.activity.compose").get())
            add("implementation", libs.findLibrary("androidx.ui").get())
            add("implementation", libs.findLibrary("androidx.ui-graphics").get())
            add("implementation", libs.findLibrary("androidx.ui.tooling.preview").get())
            add("implementation", libs.findLibrary("androidx.material3").get())
            add("debugImplementation", libs.findLibrary("androidx.ui.tooling").get())
            add("debugImplementation", libs.findLibrary("androidx.ui.test.manifest").get())
            add(
                "androidTestImplementation",
                libs.findLibrary("androidx.ui.test.junit4").get(),
            )
            add("implementation", libs.findLibrary("androidx.lifecycle.viewmodel").get())
            add(
                "implementation",
                libs.findLibrary("androidx.lifecycle.runtime.compose.android").get(),
            )

            // compose navigation
            add("implementation", libs.findLibrary("kotlinx.serialization.core").get())
            add("implementation", libs.findLibrary("androidx.navigation.compose").get())

            // compose material
            add("implementation", libs.findLibrary("androidx.material3").get())

            // compose immutable
            add("implementation", libs.findLibrary("kotlinx.collections.immutable").get())
        }
    }
}
