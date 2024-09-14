plugins {
    alias(libs.plugins.synapse.android.feature)
    alias(libs.plugins.synapse.android.library.compose)
    alias(libs.plugins.synapse.android.koin)
    kotlin("plugin.serialization")
}

android {
    namespace = "com.proton.courses"
}

dependencies {
    implementation(libs.kotlinx.serialization.json)
}