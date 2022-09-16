package com.shared.myapplication.domain.usecase.show

import com.shared.myapplication.data.feature.discover.DiscoverRepository
import com.shared.myapplication.data.feature.show.TvShowsRepository
import com.shared.myapplication.model.TvShow
import com.shared.util.FlowUseCase
import com.shared.util.toResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam

class ObserveShowsParam(val categoryId: Int)

@Factory
class ObserveShowsUseCase constructor(
      dispatcher: CoroutineDispatcher,
      private val repository: DiscoverRepository,
) : FlowUseCase<ObserveShowsParam, List<TvShow>>(dispatcher) {

    override fun execute(parameters: ObserveShowsParam): Flow<Result<List<TvShow>>> {
        return repository.observeShowsByCategoryID(parameters.categoryId).toResult()
    }

}
