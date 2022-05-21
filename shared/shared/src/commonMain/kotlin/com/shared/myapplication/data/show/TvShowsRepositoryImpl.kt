package com.shared.myapplication.data.show

import co.touchlab.kermit.Logger
import com.kuuurt.paging.multiplatform.Pager
import com.kuuurt.paging.multiplatform.PagingConfig
import com.kuuurt.paging.multiplatform.PagingData
import com.kuuurt.paging.multiplatform.PagingResult
import com.kuuurt.paging.multiplatform.helpers.cachedIn
import com.shared.myapplication.data.discover.remote.TvShowsService
import com.shared.myapplication.data.discover.remote.model.ShowDetailResponse
import com.shared.myapplication.data.lastairepisodes.LastEpisodeAirCache
import com.shared.myapplication.data.show.local.TvShowCache
import com.shared.myapplication.data.showDetail.ShowCategoryCache
import com.shared.myapplication.mapper.toAirEp
import com.shared.myapplication.mapper.toShow
import com.shared.myapplication.mapper.toShowList
import com.shared.myapplication.model.ShowCategory
import com.shared.util.CommonFlow
import com.shared.util.asCommonFlow
import com.shared.util.getErrorMessage
import com.shared.util.network.Resource
import com.shared.util.network.networkBoundResource
import com.thomaskioko.tvmaniac.datasource.cache.Show
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

private const val DEFAULT_API_PAGE = 1

class TvShowsRepositoryImpl(
    private val apiService: TvShowsService,
    private val tvShowCache: TvShowCache,
    private val epAirCacheLast: LastEpisodeAirCache,
    private val showCategoryCache: ShowCategoryCache,
    private val dispatcher: CoroutineDispatcher,
) : TvShowsRepository {

    private val coroutineScope = CoroutineScope(Job() + dispatcher)

    override fun observeShow(tvShowId: Long): Flow<Resource<Show>> = networkBoundResource(
        query = { tvShowCache.observeTvShow(tvShowId) },
        shouldFetch = { it?.status.isNullOrBlank() },
        fetch = { apiService.getTvShowDetails(tvShowId) },
        saveFetchResult = { mapAndInsert(tvShowId, it) },
        onFetchFailed = { Logger.withTag("observeShow").e { it.getErrorMessage() } },
        coroutineDispatcher = dispatcher
    )

    override suspend fun updateFollowing(showId: Long, addToWatchList: Boolean) {
        tvShowCache.updateFollowingShow(showId, addToWatchList)
    }

    override fun observeFollowing(): Flow<List<Show>> = tvShowCache.observeFollowing()

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    override fun observePagedShowsByCategoryID(
        categoryId: Int
    ): CommonFlow<PagingData<Show>> {
        val pager = Pager(
            clientScope = coroutineScope,
            config = PagingConfig(
                pageSize = 30,
                enablePlaceholders = false,
                initialLoadSize = 30
            ),
            initialKey = 2,
            getItems = { currentKey, _ ->

                val apiResponse = when (categoryId) {
                    ShowCategory.TRENDING.type -> apiService.getTrendingShows(currentKey)
                    ShowCategory.TOP_RATED.type -> apiService.getTopRatedShows(currentKey)
                    ShowCategory.POPULAR.type -> apiService.getPopularShows(currentKey)
                    else -> apiService.getTrendingShows(currentKey)
                }

                apiResponse.results
                    .map { tvShowCache.insert(it.toShow()) }

                PagingResult(
                    items = showCategoryCache.getShowsByCategoryID(categoryId).toShowList(),
                    currentKey = currentKey,
                    prevKey = { null },
                    nextKey = { apiResponse.page + DEFAULT_API_PAGE }
                )
            }
        )

        return pager.pagingData
            .distinctUntilChanged()
            .cachedIn(coroutineScope)
            .asCommonFlow()
    }

    private fun mapAndInsert(tvShowId: Long, response: ShowDetailResponse) {
        tvShowCache.insert(response.toShow())

        response.lastEpisodeToAir?.let {
            epAirCacheLast.insert(it.toAirEp(tvShowId))
        }

        response.nextEpisodeToAir?.let {
            epAirCacheLast.insert(it.toAirEp(tvShowId))
        }
    }
}
