package com.shared.myapplication.domain.usecase.detail

import com.shared.myapplication.data.show.TvShowsRepository
import com.shared.myapplication.mapper.toTvShow
import com.shared.myapplication.model.TvShow
import com.shared.util.FlowInteractor
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ObserveShowInteractor constructor(
    private val dispatcher: CoroutineDispatcher,
    private val repository: TvShowsRepository,
) : FlowInteractor<Long, TvShow>(dispatcher) {

    override fun run(params: Long): Flow<TvShow> = repository.observeShow(params)
        .map { it.data?.toTvShow() ?: TvShow.EMPTY_SHOW }
}
