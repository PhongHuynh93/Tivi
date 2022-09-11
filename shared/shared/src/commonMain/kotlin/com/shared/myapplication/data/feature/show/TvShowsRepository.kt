package com.shared.myapplication.data.feature.show

import com.kuuurt.paging.multiplatform.PagingData
import com.shared.myapplication.model.TvFollowedShow
import com.shared.myapplication.model.TvShow
import com.shared.util.CommonFlow
import com.thomaskioko.tvmaniac.datasource.cache.Show
import kotlinx.coroutines.flow.Flow

interface TvShowsRepository {

    suspend fun updateFollowing(showId: String, addToWatchList: Boolean)

    suspend fun getShow(tvShowId: String): TvShow

    fun observeFollowing(): Flow<List<TvFollowedShow>>

    fun observePagedShowsByCategoryID(
        categoryId: Int
    ): CommonFlow<PagingData<Show>>

    fun observeShow(showId: String): Flow<TvShow>
}
