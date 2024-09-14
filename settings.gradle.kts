pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Synapse"
include(":app")
include(":feature:login")
include(":feature:register")
include(":feature:home")
include(":core:data")
include(":core:domain")
include(":feature:courses")
include(":feature:profile")
include(":core:network")
