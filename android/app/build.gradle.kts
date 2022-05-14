plugins {
    `android-app-plugin`
}

android {
    kotlinOptions {
        freeCompilerArgs = listOf(
            *freeCompilerArgs.toTypedArray(),
            "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
            "-opt-in=androidx.compose.animation.ExperimentalAnimationApi",
        )
    }
}

dependencies {
    implementation(projects.shared.shared)
    implementation(projects.android.commonCompose)

    implementation(libs.accompanist.insets)
    implementation(libs.androidx.compose.activity)
    implementation(libs.accompanist.systemuicontroller)
}