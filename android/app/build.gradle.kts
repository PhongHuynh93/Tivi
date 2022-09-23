import util.libs

plugins {
    `android-app-plugin`
    id("com.google.devtools.ksp") version (libs.versions.ksp)
}

android {
    kotlinOptions {
        freeCompilerArgs = listOf(
            *freeCompilerArgs.toTypedArray(),
            "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
            "-opt-in=androidx.compose.animation.ExperimentalAnimationApi",
            "-opt-in=com.google.accompanist.pager.ExperimentalPagerApi",
            "-opt-in=dev.chrisbanes.snapper.ExperimentalSnapperApi"
        )
    }

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
    implementation(projects.shared.shared)
    implementation(projects.android.commonCompose)

    implementation(libs.androidx.compose.activity)
    implementation(libs.androidx.compose.ui.util)
    implementation(libs.androidx.compose.material.icons)

    implementation(libs.accompanist.insets)
    implementation(libs.accompanist.systemuicontroller)
    implementation(libs.accompanist.pager.core)
    implementation(libs.accompanist.pager.indicator)

    implementation(libs.koin.compose)

    implementation(libs.snapper)
}
