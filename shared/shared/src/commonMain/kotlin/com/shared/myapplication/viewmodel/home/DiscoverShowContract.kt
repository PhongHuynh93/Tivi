package com.shared.myapplication.viewmodel.home

import com.shared.myapplication.model.ShowCategory
import com.shared.myapplication.model.TvShow
import com.shared.util.Immutable
import com.shared.util.viewmodel.Action
import com.shared.util.viewmodel.Effect
import kotlinx.collections.immutable.ImmutableList

sealed interface DiscoverShowAction : Action {
    object LoadTvShows : DiscoverShowAction
    data class Error(val message: String = "") : DiscoverShowAction
}

sealed interface DiscoverShowEffect : Effect {
    data class Error(val message: String = "") : DiscoverShowEffect
}

@Immutable
data class DiscoverShow(
    val featuredShows: DiscoverShowsData,
    val trendingShows: DiscoverShowsData,
    val topRatedShows: DiscoverShowsData,
    val popularShows: DiscoverShowsData,
) {

    @Immutable
    data class DiscoverShowsData(
        val category: ShowCategory,
        val tvShows: ImmutableList<TvShow>
    )
}

@Immutable
data class TvShowUI(
    val show: TvShow,
    val following: Boolean
)
