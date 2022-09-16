package com.shared.myapplication.domain.usecase.detail

import com.shared.myapplication.data.feature.seasons.SeasonsRepository
import com.shared.myapplication.model.TvSeason
import com.shared.util.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam

class GetSeasonsParam(val tvShowId: String)

@Factory
class GetSeasonsUseCase constructor(
      dispatcher: CoroutineDispatcher,
      private val repository: SeasonsRepository,
) : UseCase<GetSeasonsParam, List<TvSeason>>(dispatcher) {

    override suspend fun execute(parameters: GetSeasonsParam): List<TvSeason> {
        return repository.getShowSeasons(parameters.tvShowId)
    }
}
