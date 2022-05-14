import util.libs

plugins {
    `kmm-domain-plugin`
    id("kotlin-parcelize")
}

android {
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
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
