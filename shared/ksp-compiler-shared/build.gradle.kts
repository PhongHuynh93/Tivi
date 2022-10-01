import util.libs

plugins {
    kotlin("multiplatform")
}

kotlin {

    // this is only used as kapt (annotation processor, so pure jvm)
    jvm()

    sourceSets {
        val commonMain by getting {
            dependencies {
                    implementation(projects.shared.kspAnnotation)
                    implementation(libs.koin.core)
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation("com.google.devtools.ksp:symbol-processing:1.7.10-1.0.6")
                implementation("com.google.devtools.ksp:symbol-processing-api:1.7.10-1.0.6")
                implementation("com.squareup:kotlinpoet:1.12.0")
                implementation("com.squareup:kotlinpoet-ksp:1.12.0")
            }
        }
    }
}
