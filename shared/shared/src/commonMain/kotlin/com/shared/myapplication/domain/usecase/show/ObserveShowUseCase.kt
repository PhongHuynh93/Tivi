package com.shared.myapplication.domain.usecase.show

import com.shared.myapplication.data.feature.show.TvShowsRepository
import com.shared.myapplication.model.TvShow
import com.shared.util.FlowUseCase
import com.shared.util.toResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

class ObserveShowParam(val showId: String)

@Factory
class ObserveShowUseCase constructor(
    dispatcher: CoroutineDispatcher,
    private val repository: TvShowsRepository,
) : FlowUseCase<ObserveShowParam, TvShow>(dispatcher) {

    override fun execute(parameters: ObserveShowParam): Flow<Result<TvShow>> {
        return repository.observeShow(parameters.showId).toResult()
    }
}
