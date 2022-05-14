package com.shared.myapplication

import com.russhwolf.settings.AndroidSettings
import com.russhwolf.settings.Settings
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single<Settings> {
        AndroidSettings(get())
    }
}
