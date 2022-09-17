package com.shared.myapplication.data.feature.lastairepisodes

import com.shared.myapplication.data.mapper.toLastAirEpisodeList
import com.shared.myapplication.model.LastAirEpisode
import org.koin.core.annotation.Single

interface LastAirEpisodeRepository {
    suspend fun getAirEpisodes(tvShowId: String): List<LastAirEpisode>
}

@Single
class LastAirEpisodeRepositoryImpl(
    private val epAirCacheLast: LastEpisodeAirCache
) : LastAirEpisodeRepository {

    override suspend fun getAirEpisodes(tvShowId: String): List<LastAirEpisode> {
        return epAirCacheLast.getShowAirEpisodes(showId = tvShowId).toLastAirEpisodeList()
    }
}
