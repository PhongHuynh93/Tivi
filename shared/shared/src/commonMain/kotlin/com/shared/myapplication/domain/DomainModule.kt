package com.shared.myapplication.domain

import com.shared.myapplication.domain.usecase.ObserveDiscoverShowsInteractor
import com.shared.myapplication.domain.usecase.ObserveFollowingInteractor
import com.shared.myapplication.domain.usecase.detail.GetGenresInteractor
import com.shared.myapplication.domain.usecase.detail.ObserveAirEpisodesInteractor
import com.shared.myapplication.domain.usecase.detail.ObserveSeasonsInteractor
import com.shared.myapplication.domain.usecase.detail.ObserveShowInteractor
import com.shared.myapplication.domain.usecase.detail.ObserveSimilarShowsInteractor
import com.shared.myapplication.domain.usecase.detail.UpdateFollowingInteractor
import org.koin.dsl.module

val domainModule = module {
    factory { ObserveDiscoverShowsInteractor(get(), get()) }
    factory { ObserveFollowingInteractor(get(), get()) }
    factory { UpdateFollowingInteractor(get(), get()) }
    factory { ObserveShowInteractor(get(), get()) }
    factory { ObserveSimilarShowsInteractor(get(), get()) }
    factory { ObserveSeasonsInteractor(get(), get()) }
    factory { GetGenresInteractor(get(), get()) }
    factory { ObserveAirEpisodesInteractor(get(), get()) }
}
