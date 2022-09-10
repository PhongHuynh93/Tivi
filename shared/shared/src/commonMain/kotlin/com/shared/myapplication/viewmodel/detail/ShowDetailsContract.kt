package com.shared.myapplication.viewmodel.detail

import com.shared.myapplication.model.LastAirEpisode
import com.shared.myapplication.model.TvGenre
import com.shared.myapplication.model.TvSeason
import com.shared.myapplication.model.TvShow
import com.shared.util.Immutable
import com.shared.util.viewmodel.Action
import com.shared.util.viewmodel.Effect
import com.shared.util.viewmodel.State
import kotlinx.collections.immutable.ImmutableList

sealed class ShowDetailAction : Action {

    class UpdateFavorite(
        val addToWatchList: Boolean
    ) : ShowDetailAction()

    class BookmarkEpisode(
        val episodeId: String
    ) : ShowDetailAction()

    class Error(val message: String = "") : ShowDetailAction()

    class SetTvShow(val tvShow: TvShow) : ShowDetailAction()

}

sealed class ShowDetailEffect : Effect {
    data class ShowDetailsError(val errorMessage: String = "") : ShowDetailEffect()

    data class WatchlistError(
        var errorMessage: String
    ) : ShowDetailEffect()
}

@Immutable
sealed class ShowDetailViewState() : State() {
    object InProgress : ShowDetailViewState()
    data class Success(
        val tvShow: TvShow,
        val similarShowList: ImmutableList<TvShow>,
        val tvSeasonUiModels: ImmutableList<TvSeason>,
        val genreUIList: ImmutableList<TvGenre>,
        val lastAirEpList: ImmutableList<LastAirEpisode>
    ) : ShowDetailViewState()
}
