plugins {
    kotlin("multiplatform")
}

kotlin {
    jvm()
    macosX64()
    iosArm32()
    iosArm64()
    iosSimulatorArm64()
    iosX64()
    linuxX64()
    linuxArm32Hfp()
    linuxMips32()
    watchosArm32()
    watchosArm64()
    watchosSimulatorArm64()
    watchosX86()
    watchosX64()
    tvosArm64()
    tvosX64()
    tvosSimulatorArm64()
    mingwX64()
}
