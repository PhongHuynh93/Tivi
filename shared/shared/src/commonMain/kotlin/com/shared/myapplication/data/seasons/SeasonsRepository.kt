package com.shared.myapplication.data.seasons

import com.shared.util.network.Resource
import com.thomaskioko.tvmaniac.datasource.cache.SelectSeasonsByShowId
import kotlinx.coroutines.flow.Flow

interface SeasonsRepository {

    fun observeShowSeasons(tvShowId: Long): Flow<Resource<List<SelectSeasonsByShowId>>>
}
