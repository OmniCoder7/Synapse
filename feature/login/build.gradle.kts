plugins {
    alias(libs.plugins.synapse.android.feature)
    alias(libs.plugins.synapse.android.library.compose)
    kotlin("plugin.serialization")
}

android {
    namespace = "com.proton.login"
}

dependencies {
    implementation(libs.kotlinx.serialization.json)
    implementation(project(":core:domain"))
}