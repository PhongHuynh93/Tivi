package com.shared.myapplication.domain.usecase.detail

import com.shared.myapplication.data.feature.lastairepisodes.LastAirEpisodeRepository
import com.shared.myapplication.data.mapper.toLastAirEpisodeList
import com.shared.myapplication.model.LastAirEpisode
import com.shared.util.FlowInteractor
import com.shared.util.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

class GetAirEpisodesParam(val tvShowId: String)
class GetAirEpisodesUseCase constructor(
    private val dispatcher: CoroutineDispatcher,
    private val repository: LastAirEpisodeRepository,
) : UseCase<GetAirEpisodesParam, List<LastAirEpisode>>(dispatcher) {

    override suspend fun execute(parameters: GetAirEpisodesParam): List<LastAirEpisode> {
        return repository.getAirEpisodes(parameters.tvShowId)
    }

}
