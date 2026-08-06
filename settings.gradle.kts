pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Car24Assignment"
include(":app")

// Core Modules
include(":core:core-common")
include(":core:core-designsystem")
include(":core:core-ui")
include(":core:core-model")
include(":core:core-domain")
include(":core:core-database")
include(":core:core-json")
include(":core:core-navigation")

// Feature Modules
include(":feature:feature-home")
include(":feature:feature-server")
include(":feature:feature-renderer")
include(":feature:feature-landing")
