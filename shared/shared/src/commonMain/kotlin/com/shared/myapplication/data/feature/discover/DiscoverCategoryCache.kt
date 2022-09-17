package com.shared.myapplication.data.feature.discover

import com.thomaskioko.tvmaniac.datasource.cache.SelectShows
import com.thomaskioko.tvmaniac.datasource.cache.Show_category
import kotlinx.coroutines.flow.Flow

interface DiscoverCategoryCache {

    fun insert(shows: List<Show_category>)

    fun observeShowsByCategoryID(id: Int): Flow<List<SelectShows>>
}
