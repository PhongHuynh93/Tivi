package com.shared.myapplication.domain.usecase.follow

import com.shared.myapplication.data.feature.show.TvShowsRepository
import com.shared.myapplication.model.TvFollowedShow
import com.shared.util.FlowUseCase
import com.shared.util.toResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

class ObserveFollowingParam()

@Factory
class ObserveFollowingUseCase constructor(
    dispatcher: CoroutineDispatcher,
    private val repository: TvShowsRepository,
) : FlowUseCase<ObserveFollowingParam, List<TvFollowedShow>>(dispatcher) {

    override fun execute(parameters: ObserveFollowingParam): Flow<Result<List<TvFollowedShow>>> {
        return repository.observeFollowing().toResult()
    }
}
