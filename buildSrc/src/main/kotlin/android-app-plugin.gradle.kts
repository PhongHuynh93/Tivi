@file:Suppress("UnstableApiUsage")

import com.android.build.gradle.internal.api.BaseVariantOutputImpl
import util.libs

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
}

android {

    compileSdk = libs.versions.android.compile.get().toInt()

    defaultConfig {
        applicationId = "com.wind.tv.android"
        minSdk = libs.versions.android.min.get().toInt()
        targetSdk = libs.versions.android.target.get().toInt()
        versionCode = 1
        versionName = "1.0"

        multiDexEnabled = true
    }

    applicationVariants.all {
        val variant = this
        variant.outputs
            .map { it as BaseVariantOutputImpl }
            .forEach { output ->
                output.outputFileName = "app-${variant.baseName}-${variant.buildType.name}-${variant.versionName}.apk"
            }
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
            multiDexEnabled = true
        }
    }

    buildFeatures {
        compose = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
}

dependencies {

    implementation(libs.koin.core)
    implementation(libs.kermit)
    implementation(libs.ktor.android)

    debugImplementation(libs.squareup.leakcanary)

    implementation(libs.androidx.core)
    implementation(libs.androidx.palette)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.compose.material.icons)
    implementation(libs.androidx.compose.ui.runtime)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.compiler)
    implementation(libs.androidx.compose.ui.tooling)
    implementation(libs.kenburns)
    implementation(libs.coil)
    implementation(libs.accompanist.insets)
    implementation(libs.androidx.lifecycle.runtime)
    implementation(libs.androidx.compose.activity)
    implementation(libs.accompanist.systemuicontroller)
}
