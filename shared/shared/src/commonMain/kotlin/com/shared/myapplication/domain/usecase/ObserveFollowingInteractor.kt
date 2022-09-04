package com.shared.myapplication.domain.usecase

import com.shared.myapplication.data.show.TvShowsRepository
import com.shared.myapplication.mapper.toTvShowList
import com.shared.myapplication.model.TvShow
import com.shared.util.FlowInteractor
import com.thomaskioko.tvmaniac.datasource.cache.Show
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ObserveFollowingInteractor constructor(
    private val dispatcher: CoroutineDispatcher,
    private val repository: TvShowsRepository,
) : FlowInteractor<Unit, List<TvShow>>(dispatcher) {

    override fun run(params: Unit): Flow<List<TvShow>> = repository.observeFollowing()
        .map { it.toTvShowList() }
}
