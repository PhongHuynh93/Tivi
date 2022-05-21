package com.shared.myapplication.data.show

import com.kuuurt.paging.multiplatform.PagingData
import com.shared.util.CommonFlow
import com.shared.util.network.Resource
import com.thomaskioko.tvmaniac.datasource.cache.Show
import kotlinx.coroutines.flow.Flow

interface TvShowsRepository {

    suspend fun updateFollowing(showId: Long, addToWatchList: Boolean)

    fun observeShow(tvShowId: Long): Flow<Resource<Show>>

    fun observeFollowing(): Flow<List<Show>>

    fun observePagedShowsByCategoryID(
        categoryId: Int
    ): CommonFlow<PagingData<Show>>
}
