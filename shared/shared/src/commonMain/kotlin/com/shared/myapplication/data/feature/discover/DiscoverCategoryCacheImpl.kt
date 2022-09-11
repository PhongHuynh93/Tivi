package com.shared.myapplication.data.feature.discover

import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import com.thomaskioko.tvmaniac.datasource.cache.SelectShows
import com.thomaskioko.tvmaniac.datasource.cache.Show
import com.thomaskioko.tvmaniac.datasource.cache.Show_category
import com.thomaskioko.tvmaniac.datasource.cache.TvManiacDatabase
import kotlinx.coroutines.flow.Flow

class DiscoverCategoryCacheImpl(
    private val database: TvManiacDatabase
) : DiscoverCategoryCache {

    private val showCategoryQuery get() = database.showCategoryQueries

    override fun insert(shows: List<Show_category>) {
        showCategoryQuery.transaction {
            shows.forEach { show ->
                showCategoryQuery.insertOrReplace(
                    show_id = show.show_id,
                    category_id = show.category_id
                )
            }
        }
    }

    override fun observeShowsByCategoryID(id: Int): Flow<List<SelectShows>> {
        return showCategoryQuery.selectShows(category_id = id.toLong())
            .asFlow()
            .mapToList()
    }
}
