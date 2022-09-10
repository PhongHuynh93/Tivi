package com.shared.myapplication.data.feature.seasons

import com.shared.myapplication.data.mapper.toSeasonCacheList
import com.shared.myapplication.data.mapper.toTvSeasonList
import com.shared.myapplication.data.service.tmdb.TmdbService
import com.shared.myapplication.model.TvSeason

interface SeasonsRepository {

    suspend fun getShowSeasons(tvShowId: String): List<TvSeason>
}

class SeasonsRepositoryImpl(
    private val apiService: TmdbService,
    private val seasonCache: SeasonsCache,
    ) : SeasonsRepository {

    override suspend fun getShowSeasons(tvShowId: String): List<TvSeason> {
        return apiService.getTvShowDetails(tvShowId)
            .also {
                seasonCache.insert(it.toSeasonCacheList())
            }
            .toTvSeasonList()
    }

}
