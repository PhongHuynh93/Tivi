package com.shared.myapplication.data.feature.show

import com.thomaskioko.tvmaniac.datasource.cache.AirEpisodesByShowId
import com.thomaskioko.tvmaniac.datasource.cache.FollowedShow
import com.thomaskioko.tvmaniac.datasource.cache.Show
import kotlinx.coroutines.flow.Flow

interface TvShowCache {

    fun insert(show: Show)

    fun insert(list: List<Show>)

    fun observeTvShow(showId: String): Flow<Show>

    fun observeTvShows(): Flow<List<Show>>

    fun observeFollowing(): Flow<List<FollowedShow>>

    fun observeShowAirEpisodes(showId: String): Flow<List<AirEpisodesByShowId>>

    fun deleteTvShows()

    fun upsertFollowing(showId: String, addToWatchList: Boolean)
}
