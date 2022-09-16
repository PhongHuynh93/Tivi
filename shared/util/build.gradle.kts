import util.libs

plugins {
    `kmm-domain-plugin`
    id("kotlin-parcelize")
    id("com.google.devtools.ksp") version (libs.versions.ksp)
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
                implementation(libs.koin.annotations)
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

    add("kspCommonMainMetadata", libs.koin.kspCompiler)
    add("kspAndroid", libs.koin.kspCompiler)
    add("kspIosX64", libs.koin.kspCompiler)
    add("kspIosSimulatorArm64", libs.koin.kspCompiler)
    add("kspIosArm64", libs.koin.kspCompiler)
}
