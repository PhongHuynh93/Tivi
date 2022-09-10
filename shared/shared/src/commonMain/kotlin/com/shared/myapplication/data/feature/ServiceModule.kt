package com.shared.myapplication.data.feature

import com.shared.myapplication.data.service.tmdb.TmdbService
import com.shared.myapplication.data.service.tmdb.TmdbServiceImpl
import com.shared.myapplication.data.service.tvShows.TvShowsService
import com.shared.myapplication.data.service.tvShows.TvShowsServiceImpl
import org.koin.dsl.module

val serviceModule = module {
    single<TvShowsService> { TvShowsServiceImpl(get()) }
    single<TmdbService> { TmdbServiceImpl(get()) }
}
