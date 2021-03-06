package com.shared.myapplication.domain.usecase

import com.shared.myapplication.data.discover.DiscoverRepository
import com.shared.myapplication.mapper.toTvShowList
import com.shared.myapplication.model.ShowCategory
import com.shared.myapplication.model.DiscoverShowResult
import com.shared.util.FlowInteractor
import com.shared.util.network.Resource
import com.shared.util.network.Status
import com.thomaskioko.tvmaniac.datasource.cache.Show
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

private const val FEATURED_LIST_SIZE = 5

class ObserveDiscoverShowsInteractor constructor(
    private val dispatcher: CoroutineDispatcher,
    private val repository: DiscoverRepository
) : FlowInteractor<Unit, DiscoverShowResult>(dispatcher) {

    override fun run(params: Unit): Flow<DiscoverShowResult> = combine(
        repository.observeShowsByCategoryID(ShowCategory.TRENDING.type).toShowData(ShowCategory.TRENDING),
        repository.observeShowsByCategoryID(ShowCategory.TOP_RATED.type).toShowData(ShowCategory.TOP_RATED),
        repository.observeShowsByCategoryID(ShowCategory.POPULAR.type).toShowData(ShowCategory.POPULAR),
    ) { trending, topRated, popular ->

        DiscoverShowResult(
            featuredShows = trending.copy(
                tvShows = trending.tvShows
                    .sortedBy { it.votes }
                    .take(FEATURED_LIST_SIZE)
            ),
            trendingShows = trending,
            popularShows = popular,
            topRatedShows = topRated
        )
    }

    private fun Flow<Resource<List<Show>>>.toShowData(category: ShowCategory) = map {
        DiscoverShowResult.DiscoverShowsData(
            isLoading = it.status == Status.LOADING,
            category = category,
            tvShows = it.data?.toTvShowList() ?: emptyList(),
            errorMessage = it.throwable?.message
        )
    }
}
