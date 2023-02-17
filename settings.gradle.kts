enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
enableFeaturePreview("VERSION_CATALOGS")

pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

rootProject.name = "Tivi"
include(":jvm:shared")
include(":jvm:common-compose")
include(":shared:shared")
include(":shared:util")
include(":shared:ksp-compiler-shared")
include(":shared:ksp-annotation")
