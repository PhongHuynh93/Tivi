package com.shared.myapplication.domain.usecase.detail

import com.shared.myapplication.data.feature.similar.SimilarShowsRepository
import com.shared.myapplication.model.TvShow
import com.shared.util.UseCase
import kotlinx.coroutines.CoroutineDispatcher

class GetSimilarShowsParam(val tvShowId: String)
class GetSimilarShowsUseCase constructor(
    private val dispatcher: CoroutineDispatcher,
    private val repository: SimilarShowsRepository,
) : UseCase<GetSimilarShowsParam, List<TvShow>>(dispatcher) {

    override suspend fun execute(parameters: GetSimilarShowsParam): List<TvShow> {
        return repository.getSimilarShows(parameters.tvShowId)
    }
}
