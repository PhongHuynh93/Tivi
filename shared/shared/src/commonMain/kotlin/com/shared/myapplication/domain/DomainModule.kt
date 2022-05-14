package com.shared.myapplication.domain

import com.shared.myapplication.domain.usecase.ObserveDiscoverShowsInteractor
import org.koin.dsl.module

val domainModule = module {
    factory { ObserveDiscoverShowsInteractor(get(), get()) }
}
