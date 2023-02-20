@file:OptIn(ExperimentalComposeLibrary::class)

import util.libs
import org.jetbrains.compose.ExperimentalComposeLibrary

plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("org.jetbrains.compose")
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

    sourceSets {
//        all {
//            languageSettings.optIn("org.jetbrains.compose.ExperimentalComposeLibrary")
//        }
        val commonMain by getting {
            dependencies {
                api(projects.shared.shared)
                api(projects.jvm.commonCompose)

                implementation(libs.koin.compose)
                implementation(libs.snapper)

                implementation(compose.desktop.currentOs)
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.materialIconsExtended)
                implementation(compose.preview)

                implementation("com.arkivanov.decompose:decompose:1.0.0")
                implementation("com.arkivanov.decompose:extensions-compose-jetbrains:1.0.0")
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

//    kotlinOptions {
//        freeCompilerArgs = listOf(
//            *freeCompilerArgs.toTypedArray(),
//            "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
//            "-opt-in=androidx.compose.animation.ExperimentalAnimationApi",
//            "-opt-in=com.google.accompanist.pager.ExperimentalPagerApi",
//            "-opt-in=dev.chrisbanes.snapper.ExperimentalSnapperApi"
//        )
//    }

}
