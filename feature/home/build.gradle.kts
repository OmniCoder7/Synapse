plugins {
    alias(libs.plugins.synapse.android.feature)
    alias(libs.plugins.synapse.android.library.compose)
    kotlin("plugin.serialization")
}

android {
    namespace = "com.proton.home"
    buildFeatures.compose = true
}
dependencies {
    implementation(libs.kotlinx.serialization.json)
    implementation(platform(libs.coil.bom))
    implementation(libs.coil.compose)

    implementation(project(":core:domain"))
}