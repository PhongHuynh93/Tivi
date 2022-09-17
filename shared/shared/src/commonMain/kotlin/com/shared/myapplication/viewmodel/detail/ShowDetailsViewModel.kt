package com.shared.myapplication.viewmodel.detail

import com.shared.myapplication.KMPViewModel
import com.shared.myapplication.domain.usecase.detail.GetAirEpisodesParam
import com.shared.myapplication.domain.usecase.detail.GetAirEpisodesUseCase
import com.shared.myapplication.domain.usecase.detail.GetGenresParam
import com.shared.myapplication.domain.usecase.detail.GetGenresUseCase
import com.shared.myapplication.domain.usecase.detail.GetSeasonsParam
import com.shared.myapplication.domain.usecase.detail.GetSeasonsUseCase
import com.shared.myapplication.domain.usecase.detail.GetShowDetailParam
import com.shared.myapplication.domain.usecase.detail.GetShowDetailUseCase
import com.shared.myapplication.domain.usecase.detail.GetSimilarShowsParam
import com.shared.myapplication.domain.usecase.detail.GetSimilarShowsUseCase
import com.shared.myapplication.domain.usecase.follow.ObserveFollowingParam
import com.shared.myapplication.domain.usecase.follow.ObserveFollowingUseCase
import com.shared.myapplication.domain.usecase.follow.UpdateFollowingParam
import com.shared.myapplication.domain.usecase.follow.UpdateFollowingUseCase
import com.shared.myapplication.viewmodel.home.TvShowUI
import com.shared.util.getErrorMessage
import com.shared.util.viewmodel.BaseViewModel
import com.shared.util.viewmodel.Store
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@KMPViewModel
class ShowDetailsViewModel :
    Store<ShowDetailViewState, ShowDetailAction, ShowDetailEffect>,
    BaseViewModel(),
    KoinComponent {

    private val getSimilarShows: GetSimilarShowsUseCase by inject()
    private val getSeasonsUseCase: GetSeasonsUseCase by inject()
    private val getGenresUseCase: GetGenresUseCase by inject()
    private val getAirEpisodesUseCase: GetAirEpisodesUseCase by inject()
    private val updateFollowingUseCase: UpdateFollowingUseCase by inject()
    private val observeFollowingUseCase: ObserveFollowingUseCase by inject()
    private val getShowUseCase: GetShowDetailUseCase by inject()

    private val _state = MutableStateFlow<ShowDetailViewState>(ShowDetailViewState.InProgress)
    override val state: StateFlow<ShowDetailViewState> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<ShowDetailEffect>()
    override val effect: SharedFlow<ShowDetailEffect> = _effect.asSharedFlow()

    private val showId = MutableStateFlow<String>("")
    private lateinit var successState: ShowDetailViewState.Success

    init {
        clientScope.launch {
            // observe follow here and update the state
            observeFollowingUseCase(ObserveFollowingParam()).map {
                it.getOrDefault(emptyList())
            }.combine(_state) { following, state ->
                when (state) {
                    ShowDetailViewState.InProgress -> state
                    is ShowDetailViewState.Success -> state.copy(
                        tvShow = state.tvShow.copy(
                            following = following.firstOrNull { followedShow ->
                                followedShow.showId == state.tvShow.show.id
                            }?.following ?: false
                        )
                    )
                }
            }.collectLatest {
                _state.value = it
            }
        }
    }

    override fun dispatch(action: ShowDetailAction) {
        when (action) {
            is ShowDetailAction.UpdateFavorite -> {
                clientScope.launch {
                    updateFollowingUseCase(
                        UpdateFollowingParam(
                            showId.value,
                            !action.addToWatchList
                        )
                    )
                }
            }
            is ShowDetailAction.Error -> {
                clientScope.launch {
                    _effect.emit(ShowDetailEffect.ShowDetailsError(action.message))
                }
            }
            is ShowDetailAction.BookmarkEpisode -> {
                // TODO Update episode watchlist
            }
            is ShowDetailAction.SetTvShow -> {
                clientScope.launch {
                    // first show the show
                    val tvShow = action.tvShow
                    showId.value = tvShow.id
                    successState = ShowDetailViewState.Success(
                        tvShow = TvShowUI(
                            show = tvShow,
                            following = false // update following state in other method
                        ),
                        similarShowList = persistentListOf(),
                        tvSeasonUiModels = persistentListOf(),
                        genreUIList = persistentListOf(),
                        lastAirEpList = persistentListOf(),
                    )
                    _state.value = successState

                    // then show the rest
                    supervisorScope {
                        try {
                            val seasonsDeferred = async {
                                getSeasonsUseCase(GetSeasonsParam(tvShow.id)).getOrThrow()
                            }
                            val genresDeferred = async {
                                getGenresUseCase(GetGenresParam(tvShow)).getOrThrow()
                            }
                            val episodesDeferred = async {
                                // need to get show detail, the episode is in the show detail
                                getShowUseCase(GetShowDetailParam(tvShow.id)).getOrThrow()
                                getAirEpisodesUseCase(GetAirEpisodesParam(tvShow.id)).getOrThrow()
                            }
                            val similarShowsDeferred = async {
                                getSimilarShows(GetSimilarShowsParam(tvShow.id)).getOrThrow()
                            }
                            val seasons = seasonsDeferred.await()
                            val genres = genresDeferred.await()
                            val episodes = episodesDeferred.await()
                            val similarShows = similarShowsDeferred.await()
                            _state.value = successState.copy(
                                similarShowList = similarShows.toImmutableList(),
                                tvSeasonUiModels = seasons.toImmutableList(),
                                genreUIList = genres.toImmutableList(),
                                lastAirEpList = episodes.toImmutableList()
                            )
                        } catch (e: Exception) {
                            dispatch(ShowDetailAction.Error(e.getErrorMessage()))
                        }
                    }
                }
            }
        }
    }
}
