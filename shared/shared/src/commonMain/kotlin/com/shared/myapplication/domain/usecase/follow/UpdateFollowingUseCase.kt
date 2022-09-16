package com.shared.myapplication.domain.usecase.follow

import com.shared.myapplication.data.feature.show.TvShowsRepository
import com.shared.util.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam

class UpdateFollowingParam(
    val showId: String,
    val addToWatchList: Boolean
)

@Factory
class UpdateFollowingUseCase constructor(
      dispatcher: CoroutineDispatcher,
      private val repository: TvShowsRepository,
) : UseCase<UpdateFollowingParam, Unit>(dispatcher) {

    override suspend fun execute(parameters: UpdateFollowingParam) {
        repository.updateFollowing(parameters.showId, parameters.addToWatchList)
    }
}
