package com.shared.myapplication

import com.shared.util.AppContext
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import com.thomaskioko.tvmaniac.datasource.cache.TvManiacDatabase

actual class DriverFactory(private val context: AppContext) {

    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(TvManiacDatabase.Schema, context, "tvShows.db")
    }
}
