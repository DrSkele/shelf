import com.skele.build_logic.convention.extension.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class RoomConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(
                    @Suppress("ktlint:standard:chain-method-continuation")
                    libs.findPlugin("ksp").get().get().pluginId,
                )
            }

            dependencies {
                add("ksp", libs.findLibrary("room.compiler").get())
                add("implementation", libs.findLibrary("room.runtime").get())
                add("implementation", libs.findLibrary("room.ktx").get())
                add("implementation", libs.findLibrary("room.testing").get())
            }
        }
    }
}
