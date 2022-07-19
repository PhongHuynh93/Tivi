package com.shared.myapplication.data.similar

import com.shared.util.network.Resource
import com.thomaskioko.tvmaniac.datasource.cache.SelectSimilarShows
import kotlinx.coroutines.flow.Flow

interface SimilarShowsRepository {

    fun observeSimilarShows(showId: Long): Flow<Resource<List<SelectSimilarShows>>>
}
