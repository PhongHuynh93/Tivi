@file:OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)

import util.libs

plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("org.jetbrains.compose")
    id("dev.icerock.mobile.multiplatform-resources")
}


kotlin {
    android()

    jvm("desktop") {
        compilations.all {
            kotlinOptions {
                jvmTarget = "11"
            }
        }
    }

    sourceSets.all {
        languageSettings.apply {
            optIn("org.jetbrains.compose.resources.ExperimentalResourceApi")
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api("dev.icerock.moko:resources-compose:0.20.1")
                implementation(compose.desktop.currentOs)
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.materialIconsExtended)
                implementation(compose.preview)
                implementation(libs.kamel)
                implementation("org.jetbrains.compose.components:components-resources:1.4.0-alpha01-dev942")
            }
        }
    }

}

android {
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")

    compileSdk = libs.versions.android.compile.get().toInt()

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }

    defaultConfig {
        minSdk = libs.versions.android.min.get().toInt()
        targetSdk = libs.versions.android.target.get().toInt()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

}

multiplatformResources {
    multiplatformResourcesPackage = "com.shared.common_compose"
}
