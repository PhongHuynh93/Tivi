package com.shared.myapplication.data.feature.show

import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import com.squareup.sqldelight.runtime.coroutines.mapToOne
import com.thomaskioko.tvmaniac.datasource.cache.AirEpisodesByShowId
import com.thomaskioko.tvmaniac.datasource.cache.FollowedShow
import com.thomaskioko.tvmaniac.datasource.cache.Show
import com.thomaskioko.tvmaniac.datasource.cache.TvManiacDatabase
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Single

@Single
class TvShowCacheImpl(
    private val database: TvManiacDatabase
) : TvShowCache {

    override fun insert(show: Show) {
        database.showQueries.insertOrReplace(
            id = show.id,
            title = show.title,
            description = show.description,
            language = show.language,
            poster_image_url = show.poster_image_url,
            backdrop_image_url = show.backdrop_image_url,
            votes = show.votes,
            vote_average = show.vote_average,
            genre_ids = show.genre_ids,
            year = show.year,
            status = show.status,
            popularity = show.popularity
        )
    }

    override fun insert(list: List<Show>) {
        database.showQueries.transaction {
            list.forEach { insert(it) }
        }
    }

    override fun observeTvShow(showId: String): Flow<Show> {
        return database.showQueries.selectByShowId(
            id = showId
        )
            .asFlow()
            .mapToOne()
    }

    override fun observeTvShows(): Flow<List<Show>> {
        return database.showQueries.selectAll()
            .asFlow()
            .mapToList()
    }

    override fun observeFollowing(): Flow<List<FollowedShow>> {
        return database.followedShowQueries.selectFollowinglist()
            .asFlow()
            .mapToList()
    }

    override fun upsertFollowing(showId: String, addToWatchList: Boolean) {
        database.followedShowQueries.upsert(
            showId,
            addToWatchList
        )
    }

    override fun observeShowAirEpisodes(showId: String): Flow<List<AirEpisodesByShowId>> {
        return database.lastAirEpisodeQueries.airEpisodesByShowId(
            show_id = showId
        ).asFlow()
            .mapToList()
    }

    override fun deleteTvShows() {
        database.showQueries.deleteAll()
    }
}
