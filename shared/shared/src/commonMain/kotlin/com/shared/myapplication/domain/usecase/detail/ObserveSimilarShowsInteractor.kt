package com.shared.myapplication.domain.usecase.detail

import com.shared.myapplication.data.similar.SimilarShowsRepository
import com.shared.myapplication.mapper.toTvShowList
import com.shared.myapplication.model.TvShow
import com.shared.util.FlowInteractor
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ObserveSimilarShowsInteractor constructor(
    private val dispatcher: CoroutineDispatcher,
    private val repository: SimilarShowsRepository,
) : FlowInteractor<Long, List<TvShow>>(dispatcher) {

    override fun run(params: Long): Flow<List<TvShow>> = repository.observeSimilarShows(params)
        .map { it.data?.toTvShowList() ?: emptyList() }
}
