package com.shared.myapplication.viewmodel.detail

import com.shared.util.Immutable
import com.shared.myapplication.domain.usecase.detail.UpdateShowParams
import com.shared.myapplication.model.GenreUIModel
import com.shared.myapplication.model.LastAirEpisode
import com.shared.myapplication.model.SeasonUiModel
import com.shared.myapplication.model.TvShow
import com.shared.util.viewmodel.Action
import com.shared.util.viewmodel.Effect
import com.shared.util.viewmodel.State
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

sealed class ShowDetailAction : Action {
    object LoadGenres : ShowDetailAction()
    object LoadSimilarShows : ShowDetailAction()

    data class LoadShowDetails(
        val showId: Long
    ) : ShowDetailAction()

    data class LoadSeasons(
        val showId: Long
    ) : ShowDetailAction()

    data class LoadEpisodes(
        val showId: Long
    ) : ShowDetailAction()

    data class UpdateFavorite(
        val params: UpdateShowParams
    ) : ShowDetailAction()

    data class BookmarkEpisode(
        val episodeNumber: Long
    ) : ShowDetailAction()

    data class Error(val message: String = "") : ShowDetailAction()

    data class SetShowID(val showId: Long) : ShowDetailAction()

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
        val tvShow: TvShow = TvShow.EMPTY_SHOW,
        val similarShowList: ImmutableList<TvShow> = persistentListOf(),
        val tvSeasonUiModels: ImmutableList<SeasonUiModel> = persistentListOf(),
        val genreUIList: ImmutableList<GenreUIModel> = persistentListOf(),
        val lastAirEpList: ImmutableList<LastAirEpisode> = persistentListOf()
    ): ShowDetailViewState()

    class Error(val errorMessage: String) : ShowDetailViewState()
}
