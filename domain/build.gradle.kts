plugins {
    `android-library`
    `kotlin-android`
    id("com.google.devtools.ksp")
}

apply<MainGradlePlugin>()

android {
    namespace = "com.proton.domain"
}

dependencies {
}