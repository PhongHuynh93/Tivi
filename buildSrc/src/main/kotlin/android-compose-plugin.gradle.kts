@file:Suppress("UnstableApiUsage")

import util.libs

plugins {
    id("com.android.library")
    kotlin("android")
}

android {

    compileSdk = libs.versions.android.compile.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.min.get().toInt()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        compose = true
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
}

dependencies {
    implementation(libs.androidx.core)
    api(libs.androidx.palette)
    api(libs.androidx.compose.runtime)
    api(libs.androidx.compose.material)
    implementation(libs.androidx.compose.material.icons)
    api(libs.androidx.compose.ui.runtime)
    implementation(libs.androidx.compose.foundation)
    api(libs.androidx.compose.compiler)
    api(libs.kotlin.coroutines.core)
    api(libs.androidx.compose.ui.tooling)
    api(libs.androidx.compose.constraintlayout)
    implementation(libs.kenburns)
    implementation(libs.coil)
    implementation(libs.accompanist.insets)
    implementation(libs.androidx.lifecycle.runtime)
}
