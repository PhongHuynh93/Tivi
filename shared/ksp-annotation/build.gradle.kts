plugins {
    kotlin("jvm")
}

sourceSets.main {
    java.srcDirs("build/generated/ksp/main/kotlin")
}
