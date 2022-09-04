package com.shared.myapplication.data.discover

import com.shared.myapplication.data.category.local.CategoryCache
import com.shared.myapplication.data.discover.local.DiscoverCategoryCache
import com.shared.myapplication.data.discover.remote.TvShowsService
import com.shared.myapplication.data.discover.remote.model.TvShowsResponse
import com.shared.myapplication.data.show.local.TvShowCache
import com.shared.myapplication.mapper.toShow
import com.shared.myapplication.mapper.toTvShowList
import com.shared.myapplication.model.ShowCategory
import com.shared.myapplication.model.TvShow
import com.thomaskioko.tvmaniac.datasource.cache.Category
import com.thomaskioko.tvmaniac.datasource.cache.Show
import com.thomaskioko.tvmaniac.datasource.cache.Show_category

private const val DEFAULT_API_PAGE = 1

class DiscoverRepositoryImpl(
    private val apiService: TvShowsService,
    private val tvShowCache: TvShowCache,
    private val discoverCategoryCache: DiscoverCategoryCache,
    private val categoryCache: CategoryCache
) : DiscoverRepository {

    override suspend fun getShowsByCategoryID(categoryId: Int): List<TvShow> {
        return fetchShowsApiRequest(categoryId).let { response ->
            val shows = response.results.map { show -> show.toShow() }
            cacheResult(shows, categoryId)
            shows.toTvShowList()
        }
    }

    private suspend fun fetchShowsApiRequest(categoryId: Int): TvShowsResponse = when (categoryId) {
        ShowCategory.TRENDING.type -> apiService.getTrendingShows(DEFAULT_API_PAGE)
        ShowCategory.POPULAR.type -> apiService.getPopularShows(DEFAULT_API_PAGE)
        ShowCategory.TOP_RATED.type -> apiService.getTopRatedShows(DEFAULT_API_PAGE)
        else -> apiService.getTrendingShows(DEFAULT_API_PAGE)
    }

    private fun cacheResult(
        shows: List<Show>,
        categoryId: Int
    ) {
        // insert list of shows
        tvShowCache.insert(shows)

        // Insert Category
        categoryCache.insert(
            Category(
                id = categoryId.toLong(),
                name = ShowCategory[categoryId].title
            )
        )

        shows.forEach { show ->
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
