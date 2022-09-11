package com.shared.myapplication.data.feature.discover

import com.shared.myapplication.data.feature.category.CategoryCache
import com.shared.myapplication.data.feature.show.TvShowCache
import com.shared.myapplication.data.mapper.toShow
import com.shared.myapplication.data.mapper.toShowList
import com.shared.myapplication.data.mapper.toTvShow
import com.shared.myapplication.data.mapper.toTvShowList
import com.shared.myapplication.data.service.model.TvShowsResponse
import com.shared.myapplication.data.service.tvShows.TvShowsService
import com.shared.myapplication.model.ShowCategory
import com.shared.myapplication.model.TvShow
import com.thomaskioko.tvmaniac.datasource.cache.Category
import com.thomaskioko.tvmaniac.datasource.cache.Show
import com.thomaskioko.tvmaniac.datasource.cache.Show_category
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

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

    override fun observeShowsByCategoryID(categoryId: Int): Flow<List<TvShow>> {
        return discoverCategoryCache.observeShowsByCategoryID(categoryId)
            .distinctUntilChanged()
            .map {
                it.toShowList().map {
                    it.toTvShow()
                }
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

        // Insert ShowCategory
        discoverCategoryCache.insert(
            shows.map { show ->
                Show_category(
                    category_id = categoryId.toLong(),
                    show_id = show.id
                )
            }

        )
    }
}
