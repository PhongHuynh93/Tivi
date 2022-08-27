import util.libs

plugins {
    `kmm-domain-plugin`
    id("kotlin-parcelize")
}

android {
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
}

kotlin {
    android()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        val commonMain by getting {}
        val androidMain by getting {
            dependencies {
                implementation(libs.androidx.compose.runtime)
            }
        }
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
    }
}

dependencies {
    commonMainApi(libs.kermit)
    commonMainApi(libs.koin.core)
    commonMainApi(libs.ktor.core)
    commonMainApi(libs.kotlin.datetime)
    commonMainApi(libs.kotlin.coroutines.core)
    commonMainApi(libs.kotlin.collections.immutable)

    androidMainApi(libs.androidx.lifecycle.viewmodelKtx)
    androidMainApi(libs.koin.android)
}
