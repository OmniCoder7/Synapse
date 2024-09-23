plugins {
    alias(libs.plugins.synapse.android.library)
    alias(libs.plugins.synapse.android.koin)
    kotlin("plugin.serialization") version "2.0.0"
}

android {
    namespace = "com.proton.network"
}

dependencies {
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.android)
    implementation(libs.ktor.client.auth)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.client.logging)
    implementation(platform(libs.ktor.client.bom))
    implementation(libs.ktor.client.serialization)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.ktor.client.gson)
}