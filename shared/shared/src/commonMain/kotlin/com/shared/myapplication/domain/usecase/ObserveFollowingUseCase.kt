package com.shared.myapplication.domain.usecase

import com.shared.myapplication.data.feature.show.TvShowsRepository
import com.shared.myapplication.data.mapper.toTvShowList
import com.shared.myapplication.model.TvShow
import com.shared.util.FlowInteractor
import com.shared.util.FlowUseCase
import com.shared.util.toResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ObserveFollowingParam()
class ObserveFollowingUseCase constructor(
    dispatcher: CoroutineDispatcher,
    private val repository: TvShowsRepository,
) : FlowUseCase<ObserveFollowingParam, List<TvShow>>(dispatcher) {

    override fun execute(parameters: ObserveFollowingParam): Flow<Result<List<TvShow>>> {
        return repository.observeFollowing().toResult()
    }

}
