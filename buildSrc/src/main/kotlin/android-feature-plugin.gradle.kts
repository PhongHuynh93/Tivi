@file:Suppress("UnstableApiUsage")

import util.libs

plugins {
    id("com.android.library")
    id("dagger.hilt.android.plugin")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdk = libs.versions.android.compile.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.min.get().toInt()
        targetSdk = libs.versions.android.target.get().toInt()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

dependencies {
//    api(project(":android:common:navigation"))
//    implementation(project(":android:common:resources"))

    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation)
    kapt(libs.hilt.compiler)

    testImplementation(libs.testing.turbine)
    testImplementation(libs.testing.coroutines.test)
    testImplementation(libs.testing.kotest.assertions)
    testRuntimeOnly(libs.testing.junit5.jupiter)
    testRuntimeOnly(libs.testing.junit5.engine)
    testRuntimeOnly(libs.testing.junit5.vintage)
}
