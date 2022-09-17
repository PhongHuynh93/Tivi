package com.shared.myapplication.domain.usecase.detail

import com.shared.myapplication.data.feature.lastairepisodes.LastAirEpisodeRepository
import com.shared.myapplication.model.LastAirEpisode
import com.shared.util.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import org.koin.core.annotation.Factory

class GetAirEpisodesParam(val tvShowId: String)

@Factory
class GetAirEpisodesUseCase constructor(
    dispatcher: CoroutineDispatcher,
    private val repository: LastAirEpisodeRepository,
) : UseCase<GetAirEpisodesParam, List<LastAirEpisode>>(dispatcher) {

    override suspend fun execute(parameters: GetAirEpisodesParam): List<LastAirEpisode> {
        return repository.getAirEpisodes(parameters.tvShowId)
    }
}
