package com.shared.myapplication.data.discover.local

import com.thomaskioko.tvmaniac.datasource.cache.SelectShows
import com.thomaskioko.tvmaniac.datasource.cache.Show_category
import kotlinx.coroutines.flow.Flow

interface DiscoverCategoryCache {

    fun insert(category: Show_category)

    fun observeShowsByCategoryID(id: Int): Flow<List<SelectShows>>
}
