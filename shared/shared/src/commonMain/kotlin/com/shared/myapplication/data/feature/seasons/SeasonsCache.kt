package com.shared.myapplication.data.feature.seasons

import com.thomaskioko.tvmaniac.datasource.cache.Season
import com.thomaskioko.tvmaniac.datasource.cache.SelectSeasonsByShowId
import kotlinx.coroutines.flow.Flow

interface SeasonsCache {

    fun insert(tvSeason: Season)

    fun insert(entityList: List<Season>)

    fun getSeasonBySeasonId(seasonId: String): Season

    fun getSeasonsByShowId(showId: String): List<SelectSeasonsByShowId>

    fun observeSeasons(tvShowId: String): Flow<List<SelectSeasonsByShowId>>
}
