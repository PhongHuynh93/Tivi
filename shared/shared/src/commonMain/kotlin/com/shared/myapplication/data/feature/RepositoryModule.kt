package com.shared.myapplication.data.feature

import com.shared.myapplication.data.feature.discover.DiscoverRepository
import com.shared.myapplication.data.feature.discover.DiscoverRepositoryImpl
import com.shared.myapplication.data.feature.genre.GenreRepository
import com.shared.myapplication.data.feature.genre.GenreRepositoryImpl
import com.shared.myapplication.data.feature.lastairepisodes.LastAirEpisodeRepository
import com.shared.myapplication.data.feature.lastairepisodes.LastAirEpisodeRepositoryImpl
import com.shared.myapplication.data.feature.seasons.SeasonsRepository
import com.shared.myapplication.data.feature.seasons.SeasonsRepositoryImpl
import com.shared.myapplication.data.feature.show.TvShowsRepository
import com.shared.myapplication.data.feature.show.TvShowsRepositoryImpl
import com.shared.myapplication.data.feature.similar.SimilarShowsRepository
import com.shared.myapplication.data.feature.similar.SimilarShowsRepositoryImpl
import com.shared.myapplication.data.service.tmdb.TmdbService
import com.shared.myapplication.data.service.tmdb.TmdbServiceImpl
import com.shared.myapplication.data.service.tvShows.TvShowsService
import com.shared.myapplication.data.service.tvShows.TvShowsServiceImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<DiscoverRepository> { DiscoverRepositoryImpl(get(), get(), get(), get()) }
    single<TvShowsRepository> { TvShowsRepositoryImpl(get(), get(), get(), get(), get()) }
    single<SeasonsRepository> { SeasonsRepositoryImpl(get(), get()) }
    single<GenreRepository> { GenreRepositoryImpl(get(), get()) }
    single<LastAirEpisodeRepository> { LastAirEpisodeRepositoryImpl(get()) }
    single<SimilarShowsRepository> { SimilarShowsRepositoryImpl(get(), get(), get()) }
}
