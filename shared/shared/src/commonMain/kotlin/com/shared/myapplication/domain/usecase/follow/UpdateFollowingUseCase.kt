package com.shared.myapplication.domain.usecase.follow

import com.shared.myapplication.data.feature.show.TvShowsRepository
import com.shared.util.UseCase
import kotlinx.coroutines.CoroutineDispatcher

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
