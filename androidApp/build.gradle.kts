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
    implementation(projects.shared)
    implementation(libs.accompanist.insetsui)
}