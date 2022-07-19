package com.shared.myapplication.domain.usecase.detail

import com.shared.myapplication.data.show.TvShowsRepository
import com.shared.util.FlowInteractor
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UpdateFollowingInteractor constructor(
    private val dispatcher: CoroutineDispatcher,
    private val repository: TvShowsRepository,
) : FlowInteractor<UpdateShowParams, Unit>(dispatcher) {

    override fun run(params: UpdateShowParams): Flow<Unit> = flow {
        repository.updateFollowing(
            showId = params.showId,
            addToWatchList = !params.addToWatchList
        )
        emit(Unit)
    }
}

data class UpdateShowParams(
    val showId: Long,
    val addToWatchList: Boolean
)
