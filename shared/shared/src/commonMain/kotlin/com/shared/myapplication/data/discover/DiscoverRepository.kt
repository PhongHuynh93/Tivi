package com.shared.myapplication.data.discover

import com.shared.myapplication.model.TvShow

interface DiscoverRepository {

    suspend fun getShowsByCategoryID(
        categoryId: Int
    ): List<TvShow>
}
