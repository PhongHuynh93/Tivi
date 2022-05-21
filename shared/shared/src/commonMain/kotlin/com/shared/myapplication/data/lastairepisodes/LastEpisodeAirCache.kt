package com.shared.myapplication.data.lastairepisodes

import com.thomaskioko.tvmaniac.datasource.cache.AirEpisodesByShowId
import com.thomaskioko.tvmaniac.datasource.cache.Last_episode
import kotlinx.coroutines.flow.Flow

interface LastEpisodeAirCache {

    fun insert(episode: Last_episode)

    fun insert(list: List<Last_episode>)

    fun getShowAirEpisodes(showId: Long): Flow<List<AirEpisodesByShowId>>
}
