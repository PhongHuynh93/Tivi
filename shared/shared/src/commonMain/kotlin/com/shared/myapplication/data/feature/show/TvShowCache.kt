package com.shared.myapplication.data.feature.show

import com.thomaskioko.tvmaniac.datasource.cache.AirEpisodesByShowId
import com.thomaskioko.tvmaniac.datasource.cache.Show
import kotlinx.coroutines.flow.Flow

interface TvShowCache {

    fun insert(show: Show)

    fun insert(list: List<Show>)

    fun observeTvShow(showId: String): Flow<Show>

    fun observeTvShows(): Flow<List<Show>>

    fun observeFollowing(): Flow<List<Show>>

    fun getShowAirEpisodes(showId: String): Flow<List<AirEpisodesByShowId>>

    fun updateFollowingShow(showId: String, following: Boolean)

    fun deleteTvShows()
}
