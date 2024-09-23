plugins {
    alias(libs.plugins.synapse.android.library)
    alias(libs.plugins.synapse.android.koin)
}

android {
    namespace = "com.proton.domain"
}

dependencies {
    implementation("androidx.paging:paging-runtime:3.3.2")
    implementation("androidx.paging:paging-compose:3.3.2")
}