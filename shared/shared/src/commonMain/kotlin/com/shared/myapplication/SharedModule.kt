package com.shared.myapplication

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.ksp.generated.module

fun sharedModule() = SharedModule().module

@Module
@ComponentScan
class SharedModule
