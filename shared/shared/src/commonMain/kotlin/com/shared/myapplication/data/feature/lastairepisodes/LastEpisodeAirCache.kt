package com.shared.myapplication.data.feature.lastairepisodes

import com.thomaskioko.tvmaniac.datasource.cache.AirEpisodesByShowId
import com.thomaskioko.tvmaniac.datasource.cache.Last_episode

interface LastEpisodeAirCache {

    fun insert(episode: Last_episode)

    fun insert(list: List<Last_episode>)

    fun getShowAirEpisodes(showId: String): List<AirEpisodesByShowId>
}
