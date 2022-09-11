package com.shared.myapplication.domain

import com.shared.myapplication.domain.usecase.detail.GetAirEpisodesUseCase
import com.shared.myapplication.domain.usecase.detail.GetGenresUseCase
import com.shared.myapplication.domain.usecase.detail.GetSeasonsUseCase
import com.shared.myapplication.domain.usecase.detail.GetShowDetailUseCase
import com.shared.myapplication.domain.usecase.detail.GetSimilarShowsUseCase
import com.shared.myapplication.domain.usecase.follow.ObserveFollowingUseCase
import com.shared.myapplication.domain.usecase.follow.UpdateFollowingUseCase
import com.shared.myapplication.domain.usecase.show.GetPopularShowsUseCase
import com.shared.myapplication.domain.usecase.show.GetTopRatedShowsUseCase
import com.shared.myapplication.domain.usecase.show.GetTrendingShowsUseCase
import com.shared.myapplication.domain.usecase.show.ObserveShowUseCase
import com.shared.myapplication.domain.usecase.show.ObserveShowsUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { GetPopularShowsUseCase(get(), get()) }
    factory { GetTopRatedShowsUseCase(get(), get()) }
    factory { GetTrendingShowsUseCase(get(), get()) }
    factory { ObserveFollowingUseCase(get(), get()) }
    factory { UpdateFollowingUseCase(get(), get()) }
    factory { GetSimilarShowsUseCase(get(), get()) }
    factory { GetSeasonsUseCase(get(), get()) }
    factory { GetGenresUseCase(get(), get()) }
    factory { GetAirEpisodesUseCase(get(), get()) }
    factory { GetShowDetailUseCase(get(), get()) }
    factory { ObserveShowUseCase(get(), get()) }
    factory { ObserveShowsUseCase(get(), get()) }
}
