package com.shared.myapplication

import co.touchlab.kermit.Logger
import com.shared.myapplication.data.dataModule
import com.shared.myapplication.domain.domainModule
import com.shared.myapplication.viewmodel.viewmodelModule
import com.shared.util.network.ObserveConnectionState
import com.shared.util.platformCoroutineDispatcher
import io.ktor.client.HttpClient
import io.ktor.client.plugins.ContentNegotiation
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.URLBuilder
import io.ktor.http.parametersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.datetime.Clock
import kotlinx.serialization.json.Json
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

private const val TMDB_API_KEY = "https://api.themoviedb.org"

fun initKoin(appModule: Module): KoinApplication {
    val koinApplication = startKoin {
        modules(
            appModule,
            platformModule,
            coreModule,
            viewmodelModule,
            domainModule,
            dataModule,
        )
    }

    return koinApplication
}

val json = Json {
    isLenient = true
    ignoreUnknownKeys = true
}

private val coreModule = module {
    single {
        platformCoroutineDispatcher
    }
    single {
        Clock.System
    }
    single {
        Logger.v { "Init core init http client" }
        HttpClient {
            install(ContentNegotiation) {
                json(json)
            }
            install(Logging) {
                logger = object : io.ktor.client.plugins.logging.Logger {
                    override fun log(message: String) {
                        Logger.v("Network message $message")
                    }
                }
                level = LogLevel.ALL
            }
            install(HttpTimeout) {
                val timeout = 30000L
                connectTimeoutMillis = timeout
                requestTimeoutMillis = timeout
                socketTimeoutMillis = timeout
            }
            install(ResponseObserver) {
                onResponse { response ->
                    Logger.d { "HTTP status: ${response.status.value}" }
                }
            }
            install(DefaultRequest) {
                val endpointUrlBuilder = URLBuilder(TMDB_API_KEY)
                url {
                    host = endpointUrlBuilder.host
                    protocol = endpointUrlBuilder.protocol
                }
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                parametersOf("api_key", BuildKonfig.TMDB_API_KEY)
            }
        }
    }
    single {
        ObserveConnectionState(get())
    }
}

expect val platformModule: Module
