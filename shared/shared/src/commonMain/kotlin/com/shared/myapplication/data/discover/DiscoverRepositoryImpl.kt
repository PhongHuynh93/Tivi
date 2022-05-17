package com.shared.myapplication.data.discover

import co.touchlab.kermit.Logger
import com.shared.myapplication.data.category.local.CategoryCache
import com.shared.myapplication.data.discover.local.DiscoverCategoryCache
import com.shared.myapplication.data.discover.remote.TvShowsService
import com.shared.myapplication.data.discover.remote.model.TvShowsResponse
import com.shared.myapplication.model.ShowCategory
import com.shared.myapplication.data.show.local.TvShowCache
import com.shared.myapplication.mapper.toShow
import com.shared.myapplication.mapper.toShowList
import com.shared.util.getErrorMessage
import com.shared.util.network.Resource
import com.shared.util.network.networkBoundResource
import com.thomaskioko.tvmaniac.datasource.cache.Category
import com.thomaskioko.tvmaniac.datasource.cache.Show
import com.thomaskioko.tvmaniac.datasource.cache.Show_category
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val DEFAULT_API_PAGE = 1

class DiscoverRepositoryImpl(
    private val apiService: TvShowsService,
    private val tvShowCache: TvShowCache,
    private val discoverCategoryCache: DiscoverCategoryCache,
    private val categoryCache: CategoryCache,
    private val dispatcher: CoroutineDispatcher,
) : DiscoverRepository {

    override fun observeShowsByCategoryID(categoryId: Int): Flow<Resource<List<Show>>> {
        val networkBoundResource = networkBoundResource(
            query = {
                discoverCategoryCache.observeShowsByCategoryID(categoryId)
                    .map { it.toShowList() }
            },
            shouldFetch = { it.isNullOrEmpty() },
            fetch = { fetchShowsApiRequest(categoryId) },
            saveFetchResult = { cacheResult(it, categoryId) },
            onFetchFailed = {
                Logger.withTag("observeShowsByCategoryID").e { it.getErrorMessage() }
            },
            coroutineDispatcher = dispatcher
        )
        return networkBoundResource
    }

    private suspend fun fetchShowsApiRequest(categoryId: Int): TvShowsResponse = when (categoryId) {
        ShowCategory.TRENDING.type -> apiService.getTrendingShows(DEFAULT_API_PAGE)
        ShowCategory.POPULAR.type -> apiService.getPopularShows(DEFAULT_API_PAGE)
        ShowCategory.TOP_RATED.type -> apiService.getTopRatedShows(DEFAULT_API_PAGE)
        else -> apiService.getTrendingShows(DEFAULT_API_PAGE)
    }

    private fun cacheResult(
        it: TvShowsResponse,
        categoryId: Int
    ) {
        val result = it.results.map { show -> show.toShow() }
        tvShowCache.insert(result)

        result.forEach { show ->
            // Insert Category
            categoryCache.insert(
                Category(
                    id = categoryId.toLong(),
                    name = ShowCategory[categoryId].title
                )
            )

            // Insert ShowCategory
            discoverCategoryCache.insert(
                Show_category(
                    category_id = categoryId.toLong(),
                    show_id = show.id
                )
            )
        }
    }
}
