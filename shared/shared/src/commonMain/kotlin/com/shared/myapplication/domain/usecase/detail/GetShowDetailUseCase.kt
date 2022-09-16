package com.shared.myapplication.domain.usecase.detail

import com.shared.myapplication.data.feature.show.TvShowsRepository
import com.shared.myapplication.data.feature.similar.SimilarShowsRepository
import com.shared.myapplication.model.TvShow
import com.shared.util.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam

class GetShowDetailParam(val tvShowId: String)

@Factory
class GetShowDetailUseCase constructor(
      private val dispatcher: CoroutineDispatcher,
      private val repository: TvShowsRepository,
) : UseCase<GetShowDetailParam, TvShow>(dispatcher) {

    override suspend fun execute(parameters: GetShowDetailParam): TvShow {
        return repository.getShow(parameters.tvShowId)
    }
}
