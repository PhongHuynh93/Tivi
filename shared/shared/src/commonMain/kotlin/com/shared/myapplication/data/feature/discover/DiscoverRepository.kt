package com.shared.myapplication.data.feature.discover

import com.shared.myapplication.model.TvShow

interface DiscoverRepository {

    suspend fun getShowsByCategoryID(
        categoryId: Int
    ): List<TvShow>
}
