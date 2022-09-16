package com.shared.myapplication.domain.usecase.detail

import com.shared.myapplication.data.feature.similar.SimilarShowsRepository
import com.shared.myapplication.model.TvShow
import com.shared.util.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam

class GetSimilarShowsParam(val tvShowId: String)

@Factory
class GetSimilarShowsUseCase constructor(
      dispatcher: CoroutineDispatcher,
      private val repository: SimilarShowsRepository,
) : UseCase<GetSimilarShowsParam, List<TvShow>>(dispatcher) {

    override suspend fun execute(parameters: GetSimilarShowsParam): List<TvShow> {
        return repository.getSimilarShows(parameters.tvShowId)
    }
}
