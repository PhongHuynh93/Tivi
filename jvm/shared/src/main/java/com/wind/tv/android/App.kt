package com.wind.tv.android

import android.app.Application
import com.shared.myapplication.initKoin
import com.shared.util.AppContext
import org.koin.dsl.module

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin(
            module {
                single<AppContext> { this@App }
            },
        )
    }
}
