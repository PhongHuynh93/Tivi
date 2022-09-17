plugins {
    kotlin("multiplatform")
    id("java-library")
}

kotlin {

    //this is only used as kapt (annotation processor, so pure jvm)
    jvm {
        val main by compilations.getting {
            kotlinOptions {
                jvmTarget = "11"
            }
        }
    }
    
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation("com.google.devtools.ksp:symbol-processing-api:1.7.10-1.0.6")
                implementation("com.squareup:kotlinpoet:1.12.0")
                implementation("com.squareup:kotlinpoet-ksp:1.12.0")
            }
        }
    }
}
