package com.shared.myapplication.data.feature.show

import com.kuuurt.paging.multiplatform.Pager
import com.kuuurt.paging.multiplatform.PagingConfig
import com.kuuurt.paging.multiplatform.PagingData
import com.kuuurt.paging.multiplatform.PagingResult
import com.kuuurt.paging.multiplatform.helpers.cachedIn
import com.shared.myapplication.data.feature.lastairepisodes.LastEpisodeAirCache
import com.shared.myapplication.data.feature.showDetail.ShowCategoryCache
import com.shared.myapplication.data.mapper.toAirEp
import com.shared.myapplication.data.mapper.toShow
import com.shared.myapplication.data.mapper.toShowList
import com.shared.myapplication.data.mapper.toTvFollowedShow
import com.shared.myapplication.data.mapper.toTvShow
import com.shared.myapplication.data.service.model.ShowDetailResponse
import com.shared.myapplication.data.service.tvShows.TvShowsService
import com.shared.myapplication.model.ShowCategory
import com.shared.myapplication.model.TvFollowedShow
import com.shared.myapplication.model.TvShow
import com.shared.util.CommonFlow
import com.shared.util.asCommonFlow
import com.thomaskioko.tvmaniac.datasource.cache.Show
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Single

private const val DEFAULT_API_PAGE = 1

@Single
class TvShowsRepositoryImpl(
    private val apiService: TvShowsService,
    private val tvShowCache: TvShowCache,
    private val epAirCacheLast: LastEpisodeAirCache,
    private val showCategoryCache: ShowCategoryCache,
    private val dispatcher: CoroutineDispatcher,
) : TvShowsRepository {

    private val coroutineScope = CoroutineScope(Job() + dispatcher)

    override suspend fun getShow(tvShowId: String): TvShow {
        return apiService.getTvShowDetails(tvShowId).also {
            mapAndInsert(tvShowId, it)
        }.toShow().toTvShow()
    }

    override fun observeShow(showId: String): Flow<TvShow> {
        return tvShowCache.observeTvShow(showId)
            .distinctUntilChanged()
            .map {
                it.toTvShow()
            }
    }

    override suspend fun updateFollowing(showId: String, addToWatchList: Boolean) {
        tvShowCache.upsertFollowing(showId, addToWatchList)
    }

    override fun observeFollowing(): Flow<List<TvFollowedShow>> =
        tvShowCache.observeFollowing().distinctUntilChanged().map {
            it.map {
                it.toTvFollowedShow()
            }
        }

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

    private fun mapAndInsert(tvShowId: String, response: ShowDetailResponse) {
        tvShowCache.insert(response.toShow())

        response.lastEpisodeToAir?.let {
            epAirCacheLast.insert(it.toAirEp(tvShowId))
        }

        response.nextEpisodeToAir?.let {
            epAirCacheLast.insert(it.toAirEp(tvShowId))
        }
    }
}
