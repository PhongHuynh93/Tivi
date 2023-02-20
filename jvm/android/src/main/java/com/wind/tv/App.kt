package com.wind.tv

import android.app.Application
import com.shared.util.AppContext

import com.wind.tv.android.initKoin
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
