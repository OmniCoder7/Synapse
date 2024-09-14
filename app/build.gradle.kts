plugins {
    alias(libs.plugins.synapse.android.application.compose)
    alias(libs.plugins.synapse.android.application)
    alias(libs.plugins.synapse.android.koin)
    id("com.jraska.module.graph.assertion")
    kotlin("plugin.serialization")
}

android {
    namespace = "com.proton.synapse"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.proton.synapse"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
}
moduleGraphAssert {
    maxHeight = 4
    allowed = arrayOf(
        ":.* -> :feature:.*",
        ":.* -> :core:.*",
    )
    restricted = arrayOf(
        ":feature:.* -X> :feature:.*",
        ":core:.* -X> :feature"
    )
    assertOnAnyBuild = false
}
dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.ui.text.google.fonts)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    implementation(libs.androidx.compose.ui.tooling.preview)
    debugImplementation(libs.androidx.compose.ui.tooling.preview)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(project(":feature:login"))
    implementation(project(":feature:register"))
    implementation(project(":feature:home"))
    implementation(project(":feature:profile"))
    implementation(project(":feature:login"))
    implementation(project(":core:domain"))
    implementation(project(":core:data"))
    implementation(project(":core:network"))

    implementation(libs.androidx.core.splashscreen)
    implementation(libs.navigation.compose)
    implementation(libs.navigation.runtime.ktx)

    implementation(libs.coil.compose)
    implementation(platform(libs.coil.bom))

    implementation(libs.kotlinx.serialization.json)
}