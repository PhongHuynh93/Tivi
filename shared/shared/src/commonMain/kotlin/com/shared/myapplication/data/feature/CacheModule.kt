package com.shared.myapplication.data.feature

import com.shared.myapplication.data.feature.category.CategoryCache
import com.shared.myapplication.data.feature.category.CategoryCacheImpl
import com.shared.myapplication.data.feature.discover.DiscoverCategoryCache
import com.shared.myapplication.data.feature.discover.DiscoverCategoryCacheImpl
import com.shared.myapplication.data.feature.genre.GenreCache
import com.shared.myapplication.data.feature.genre.GenreCacheImpl
import com.shared.myapplication.data.feature.lastairepisodes.LastEpisodeAirCache
import com.shared.myapplication.data.feature.lastairepisodes.LastEpisodeAirCacheImpl
import com.shared.myapplication.data.feature.seasons.SeasonsCache
import com.shared.myapplication.data.feature.seasons.SeasonsCacheImpl
import com.shared.myapplication.data.feature.show.TvShowCache
import com.shared.myapplication.data.feature.show.TvShowCacheImpl
import com.shared.myapplication.data.feature.showDetail.ShowCategoryCache
import com.shared.myapplication.data.feature.showDetail.ShowCategoryCacheImpl
import com.shared.myapplication.data.feature.similar.SimilarShowCache
import com.shared.myapplication.data.feature.similar.SimilarShowCacheImpl
import org.koin.dsl.module

val cacheModule = module {
    single<TvShowCache> { TvShowCacheImpl(get()) }
    single<DiscoverCategoryCache> { DiscoverCategoryCacheImpl(get()) }
    single<CategoryCache> { CategoryCacheImpl(get()) }
    single<LastEpisodeAirCache> { LastEpisodeAirCacheImpl(get()) }
    single<ShowCategoryCache> { ShowCategoryCacheImpl(get()) }
    single<GenreCache> { GenreCacheImpl(get()) }
    single<SimilarShowCache> { SimilarShowCacheImpl(get()) }
    single<SeasonsCache> { SeasonsCacheImpl(get()) }
}
