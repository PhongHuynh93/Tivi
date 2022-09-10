package com.shared.myapplication.data.feature.similar

import com.shared.myapplication.data.feature.show.TvShowCache
import com.shared.myapplication.data.mapper.toShow
import com.shared.myapplication.data.mapper.toTvShowList
import com.shared.myapplication.data.service.tmdb.TmdbService
import com.shared.myapplication.model.TvShow

interface SimilarShowsRepository {

    suspend fun getSimilarShows(showId: String): List<TvShow>
}

class SimilarShowsRepositoryImpl(
    private val apiService: TmdbService,
    private val similarShowCache: SimilarShowCache,
    private val tvShowCache: TvShowCache
) : SimilarShowsRepository {
    override suspend fun getSimilarShows(showId: String): List<TvShow> {
        return apiService.getSimilarShows(showId).results.map {
            it.toShow()
        }.onEach { show ->
            tvShowCache.insert(show)

            similarShowCache.insert(
                showId = showId,
                similarShowId = show.id
            )
        }.toTvShowList()
    }

}
