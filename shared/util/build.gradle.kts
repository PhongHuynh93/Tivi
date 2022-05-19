import util.libs

plugins {
    `kmm-domain-plugin`
    id("kotlin-parcelize")
}

android {
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
}

kotlin {
    android()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        val commonMain by getting {}
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
    commonMainImplementation(libs.kermit)
    commonMainImplementation(libs.koin.core)
    commonMainImplementation(libs.ktor.core)
    commonMainImplementation(libs.kotlin.datetime)
    commonMainImplementation(libs.kotlin.coroutines.core)

    androidMainImplementation(libs.androidx.lifecycle.viewmodelKtx)
    androidMainImplementation(libs.koin.android)
}
