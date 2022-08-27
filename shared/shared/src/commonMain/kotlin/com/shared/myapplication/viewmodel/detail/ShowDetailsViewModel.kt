package com.shared.myapplication.viewmodel.detail

import com.shared.myapplication.domain.usecase.detail.GetGenresInteractor
import com.shared.myapplication.domain.usecase.detail.ObserveAirEpisodesInteractor
import com.shared.myapplication.domain.usecase.detail.ObserveSeasonsInteractor
import com.shared.myapplication.domain.usecase.detail.ObserveShowInteractor
import com.shared.myapplication.domain.usecase.detail.ObserveSimilarShowsInteractor
import com.shared.myapplication.domain.usecase.detail.UpdateFollowingInteractor
import com.shared.util.viewmodel.BaseViewModel
import com.shared.util.viewmodel.Store
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ShowDetailsViewModel constructor(

) : Store<ShowDetailViewState, ShowDetailAction, ShowDetailEffect>,
    BaseViewModel(), KoinComponent {

    private val observeShow: ObserveShowInteractor by inject()
    private val observeSimilarShows: ObserveSimilarShowsInteractor by inject()
    private val observeSeasonsInteractor: ObserveSeasonsInteractor by inject()
    private val genresInteractor: GetGenresInteractor by inject()
    private val observeAirEpisodesInteractor: ObserveAirEpisodesInteractor by inject()
    private val updateFollowingInteractor: UpdateFollowingInteractor by inject()

    private var showId: Long = 0

    private val emptyState = ShowDetailViewState.Success()

    private val _state = MutableStateFlow<ShowDetailViewState>(ShowDetailViewState.InProgress)
    override val state: StateFlow<ShowDetailViewState> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<ShowDetailEffect>()
    override val effect: SharedFlow<ShowDetailEffect> = _effect.asSharedFlow()

    override fun attach() {
        dispatch(ShowDetailAction.LoadShowDetails(showId))
        dispatch(ShowDetailAction.LoadSeasons(showId))
        dispatch(ShowDetailAction.LoadGenres)
        dispatch(ShowDetailAction.LoadEpisodes(showId))
        dispatch(ShowDetailAction.LoadSimilarShows)
    }

    override fun dispatch(action: ShowDetailAction) {
        when (action) {
            ShowDetailAction.LoadGenres -> fetchGenres()
            ShowDetailAction.LoadSimilarShows -> fetchSimilarShows()
            is ShowDetailAction.LoadSeasons -> fetchSeason()
            is ShowDetailAction.LoadShowDetails -> loadShowDetails()
            is ShowDetailAction.UpdateFavorite -> updateWatchlist(action)
            is ShowDetailAction.Error -> {
                clientScope.launch {
                    _effect.emit(ShowDetailEffect.ShowDetailsError(action.message))
                }
            }
            is ShowDetailAction.LoadEpisodes -> fetchEpisodes()
            is ShowDetailAction.BookmarkEpisode -> {
                // TODO Update episode watchlist
            }
            is ShowDetailAction.SetShowID -> {
                // TODO: handle set show id in view
                showId = action.showId
                attach()
            }
        }
    }

    private fun loadShowDetails() {
        observeShow.execute(clientScope, showId) {
            onNext {
                _state.value = emptyState.copy(
                    tvShow = it
                )
            }
            onError {
                _state.value = ShowDetailViewState.Error(
                    errorMessage = it.message ?: "Something went wrong"
                )
                dispatch(ShowDetailAction.Error(it.message ?: "Something went wrong"))
            }
        }
    }

    private fun fetchGenres() {
        genresInteractor.execute(clientScope, Unit) {
            onNext {
                _state.value = emptyState.copy(
                    genreUIList = it.toImmutableList()
                )
            }
            onError {
                _state.value = ShowDetailViewState.Error(
                    errorMessage = it.message ?: "Something went wrong"
                )
                dispatch(ShowDetailAction.Error(it.message ?: "Something went wrong"))
            }
        }
    }

    private fun updateWatchlist(action: ShowDetailAction.UpdateFavorite) {
        updateFollowingInteractor.execute(clientScope, action.params) {
            onComplete { dispatch(ShowDetailAction.LoadShowDetails(showId)) }
            onError {
                clientScope.launch {
                    _effect.emit(
                        ShowDetailEffect.WatchlistError(it.message ?: "Something went wrong")
                    )
                }
            }
        }
    }

    private fun fetchSeason() {
        observeSeasonsInteractor.execute(clientScope, showId) {
            onNext {
                _state.value = emptyState.copy(
                    tvSeasonUiModels = it.toImmutableList()
                )
            }
            onError {
                _state.value = ShowDetailViewState.Error(
                    errorMessage = it.message ?: "Something went wrong"
                )
            }
        }
    }

    private fun fetchEpisodes() {
        observeAirEpisodesInteractor.execute(clientScope, showId) {
            onNext {
                _state.value = emptyState.copy(
                    lastAirEpList = it.toImmutableList()
                )
            }
            onError {
                _state.value = ShowDetailViewState.Error(
                    errorMessage = it.message ?: "Something went wrong"
                )
            }
        }
    }

    private fun fetchSimilarShows() {
        observeSimilarShows.execute(clientScope, showId) {
            onNext {
                _state.value = emptyState.copy(
                    similarShowList = it.toImmutableList()
                )
            }
            onError {
                _state.value = ShowDetailViewState.Error(
                    errorMessage = it.message ?: "Something went wrong"
                )
                dispatch(ShowDetailAction.Error(it.message ?: "Something went wrong"))
            }
        }
    }
}
