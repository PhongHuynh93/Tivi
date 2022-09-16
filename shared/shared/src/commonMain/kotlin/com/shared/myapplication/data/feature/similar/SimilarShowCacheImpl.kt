package com.shared.myapplication.data.feature.similar

import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import com.thomaskioko.tvmaniac.datasource.cache.SelectSimilarShows
import com.thomaskioko.tvmaniac.datasource.cache.TvManiacDatabase
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Single

@Single
class SimilarShowCacheImpl(
    private val database: TvManiacDatabase
) : SimilarShowCache {

    override fun insert(showId: String, similarShowId: String) {
        database.showQueries.transaction {
            database.similarShowQueries.insertOrReplace(
                id = similarShowId,
                show_id = showId
            )
        }
    }

    override fun observeSimilarShows(showId: String): Flow<List<SelectSimilarShows>> {
        return database.similarShowQueries.selectSimilarShows(show_id = showId)
            .asFlow()
            .mapToList()
    }

}
