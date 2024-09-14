import com.proton.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("synapse.android.library")
                apply("synapse.android.koin")
            }
            dependencies {
                add("implementation", libs.findLibrary("androidx.lifecycle.runtimeCompose").get())
                add("implementation", libs.findLibrary("androidx.lifecycle.viewModelCompose").get())
                add("androidTestImplementation",libs.findLibrary("androidx.lifecycle.runtimeTesting").get())

                add("implementation", libs.findLibrary("navigation.runtime.ktx").get())
                add("implementation", libs.findLibrary("navigation.compose").get())

                add("implementation", libs.findLibrary("material3.android").get())
                add("implementation", libs.findLibrary("material.android").get())
            }
        }
    }
}