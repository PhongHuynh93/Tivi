package com.shared.myapplication.data.feature.showDetail

import com.thomaskioko.tvmaniac.datasource.cache.SelectShows
import com.thomaskioko.tvmaniac.datasource.cache.TvManiacDatabase

class ShowCategoryCacheImpl(
    private val database: TvManiacDatabase
) : ShowCategoryCache {

    private val showCategoryQuery get() = database.showCategoryQueries

    override fun getShowsByCategoryID(id: Int): List<SelectShows> {
        return showCategoryQuery.selectShows(
            category_id = id.toLong()
        ).executeAsList()
    }
}
