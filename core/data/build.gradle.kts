import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.synapse.android.library)
    alias(libs.plugins.synapse.android.koin)
    alias(libs.plugins.synapse.android.room)
    kotlin("plugin.serialization")
}

android {
    namespace = "com.proton.data"
}

dependencies {
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.datastore.preferences)

    implementation(project(":core:network"))
    implementation(project(":core:domain"))
    implementation (libs.jbcrypt)
}