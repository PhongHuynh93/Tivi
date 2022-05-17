package com.shared.myapplication.data

import com.shared.myapplication.data.category.local.CategoryCache
import com.shared.myapplication.data.category.local.CategoryCacheImpl
import com.shared.myapplication.data.discover.DiscoverRepository
import com.shared.myapplication.data.discover.DiscoverRepositoryImpl
import com.shared.myapplication.data.discover.local.DiscoverCategoryCache
import com.shared.myapplication.data.discover.local.DiscoverCategoryCacheImpl
import com.shared.myapplication.data.discover.remote.TvShowsService
import com.shared.myapplication.data.discover.remote.TvShowsServiceImpl
import com.shared.myapplication.data.show.local.TvShowCache
import com.shared.myapplication.data.show.local.TvShowCacheImpl
import org.koin.dsl.module

val dataModule = module {
    single<TvShowsService> { TvShowsServiceImpl(get()) }
    single<TvShowCache> { TvShowCacheImpl(get()) }
    single<DiscoverCategoryCache> { DiscoverCategoryCacheImpl(get()) }
    single<CategoryCache> { CategoryCacheImpl(get()) }
    single<DiscoverRepository> { DiscoverRepositoryImpl(get(), get(), get(), get(), get()) }
}
