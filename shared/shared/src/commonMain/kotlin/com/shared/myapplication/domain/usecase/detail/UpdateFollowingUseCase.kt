package com.shared.myapplication.domain.usecase.detail

import com.shared.myapplication.data.feature.show.TvShowsRepository
import com.shared.util.FlowInteractor
import com.shared.util.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UpdateFollowingParam(
    val showId: String,
    val addToWatchList: Boolean
)
class UpdateFollowingUseCase constructor(
    dispatcher: CoroutineDispatcher,
    private val repository: TvShowsRepository,
) : UseCase<UpdateFollowingParam, Unit>(dispatcher) {

    override suspend fun execute(parameters: UpdateFollowingParam) {
        repository.updateFollowing(parameters.showId, parameters.addToWatchList)
    }
}
