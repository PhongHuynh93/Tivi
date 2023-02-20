import util.libs

plugins {
    `android-app-plugin`
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}

android {
    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {
    api(projects.jvm.shared)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.compose.activity)
    implementation(libs.koin.android)
    implementation(libs.koin.compose)
    implementation(libs.accompanist.systemuicontroller)
    implementation(libs.kamel)

    implementation(platform(libs.firebase))
    implementation("com.google.firebase:firebase-crashlytics-ktx")
    implementation("com.google.firebase:firebase-analytics-ktx")
}