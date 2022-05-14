package com.shared.myapplication.model

import com.shared.util.viewmodel.Action
import com.shared.util.viewmodel.Effect
import com.shared.util.viewmodel.State

data class DiscoverShowState(
    val isLoading: Boolean,
    val showData: DiscoverShowResult,
) : State {
    companion object {
        val Empty = DiscoverShowState(
            isLoading = true,
            showData = DiscoverShowResult.EMPTY,
        )
    }
}

sealed class DiscoverShowAction : Action {
    object LoadTvShows : DiscoverShowAction()
    data class Error(val message: String = "") : DiscoverShowAction()
}

sealed class DiscoverShowEffect : Effect {
    data class Error(val message: String = "") : DiscoverShowEffect()
}

data class DiscoverShowResult(
    val featuredShows: DiscoverShowsData,
    val trendingShows: DiscoverShowsData,
    val topRatedShows: DiscoverShowsData,
    val popularShows: DiscoverShowsData,
) {
    companion object {
        val EMPTY = DiscoverShowResult(
            featuredShows = DiscoverShowsData.EMPTY,
            trendingShows = DiscoverShowsData.EMPTY,
            topRatedShows = DiscoverShowsData.EMPTY,
            popularShows = DiscoverShowsData.EMPTY
        )
    }

    data class DiscoverShowsData(
        val isLoading: Boolean,
        val category: ShowCategory,
        val tvShows: List<TvShow>,
        val errorMessage: String? = null
    ) {

        companion object {
            val EMPTY = DiscoverShowsData(
                isLoading = true,
                category = ShowCategory.TOP_RATED,
                tvShows = emptyList(),
                errorMessage = null
            )
        }
    }
}
