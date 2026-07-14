pluginManagement {
    this.repositories {
        this.google {
            this.content {
                this.includeGroupByRegex("com\\.android.*")
                this.includeGroupByRegex("com\\.google.*")
                this.includeGroupByRegex("androidx.*")
            }
        }
        this.mavenCentral()
        this.gradlePluginPortal()
    }
}
plugins {
    this.id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
dependencyResolutionManagement {
    this.repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    this.repositories {
        this.google()
        this.mavenCentral()
    }
}

rootProject.name = "PushPull"
include(":app")
 