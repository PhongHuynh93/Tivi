package com.shared.myapplication.viewmodel.home

import com.shared.util.Immutable
import com.shared.myapplication.model.ShowCategory
import com.shared.myapplication.model.TvShow
import com.shared.util.viewmodel.Action
import com.shared.util.viewmodel.Effect
import com.shared.util.viewmodel.State
import kotlinx.collections.immutable.ImmutableList

@Immutable
sealed class DiscoverShowState : State() {
    object InProgress : DiscoverShowState()
    class Success(val data: DiscoverShowResult) : DiscoverShowState()
}

sealed class DiscoverShowAction : Action {
    object LoadTvShows : DiscoverShowAction()
    data class Error(val message: String = "") : DiscoverShowAction()
}

sealed class DiscoverShowEffect : Effect {
    data class Error(val message: String = "") : DiscoverShowEffect()
}

@Immutable
data class DiscoverShowResult(
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
