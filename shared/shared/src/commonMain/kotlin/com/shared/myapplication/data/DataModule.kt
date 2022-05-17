package com.shared.myapplication.data

import com.shared.myapplication.data.discover.DiscoverRepository
import com.shared.myapplication.data.discover.DiscoverRepositoryImpl
import com.shared.myapplication.data.discover.local.TvShowCache
import com.shared.myapplication.data.discover.local.TvShowCacheImpl
import com.shared.myapplication.data.discover.remote.TvShowsService
import com.shared.myapplication.data.discover.remote.TvShowsServiceImpl
import org.koin.dsl.module

val dataModule = module {
    single<TvShowsService> { TvShowsServiceImpl(get()) }
    single<TvShowCache> { TvShowCacheImpl(get()) }
    single<DiscoverRepository> { DiscoverRepositoryImpl(get(), get(), get(), get(), get()) }
}
