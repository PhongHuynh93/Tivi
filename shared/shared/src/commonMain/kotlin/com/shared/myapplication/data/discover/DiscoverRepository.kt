package com.shared.myapplication.data.discover

import com.shared.util.network.Resource
import kotlinx.coroutines.flow.Flow
import com.thomaskioko.tvmaniac.datasource.cache.Show

interface DiscoverRepository {

    fun observeShowsByCategoryID(
        categoryId: Int
    ): Flow<Resource<List<Show>>>
}
