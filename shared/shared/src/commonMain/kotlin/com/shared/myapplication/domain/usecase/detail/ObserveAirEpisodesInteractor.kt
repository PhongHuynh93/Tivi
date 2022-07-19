package com.shared.myapplication.domain.usecase.detail

import com.shared.myapplication.data.lastairepisodes.LastAirEpisodeRepository
import com.shared.myapplication.mapper.toLastAirEpisodeList
import com.shared.myapplication.model.LastAirEpisode
import com.shared.util.FlowInteractor
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

class ObserveAirEpisodesInteractor constructor(
    private val dispatcher: CoroutineDispatcher,
    private val repository: LastAirEpisodeRepository,
) : FlowInteractor<Long, List<LastAirEpisode>>(dispatcher) {

    override fun run(params: Long): Flow<List<LastAirEpisode>> =
        repository.observeAirEpisodes(params)
            .map {
                val toLastAirEpisodeList = it.toLastAirEpisodeList()
                toLastAirEpisodeList
            }
            .distinctUntilChanged()
}
