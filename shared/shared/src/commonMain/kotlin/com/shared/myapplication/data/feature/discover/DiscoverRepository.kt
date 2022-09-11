package com.shared.myapplication.data.feature.discover

import com.shared.myapplication.model.TvShow
import kotlinx.coroutines.flow.Flow

interface DiscoverRepository {

    suspend fun getShowsByCategoryID(
        categoryId: Int
    ): List<TvShow>

    fun observeShowsByCategoryID(
        categoryId: Int
    ): Flow<List<TvShow>>

}
