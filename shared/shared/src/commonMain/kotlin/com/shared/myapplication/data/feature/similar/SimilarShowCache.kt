package com.shared.myapplication.data.feature.similar

import com.thomaskioko.tvmaniac.datasource.cache.SelectSimilarShows
import kotlinx.coroutines.flow.Flow

interface SimilarShowCache {

    fun insert(showId: String, similarShowId: String)

    fun observeSimilarShows(showId: String): Flow<List<SelectSimilarShows>>
}
