package com.shared.myapplication.viewmodel.detail

import co.touchlab.kermit.Logger
import com.shared.myapplication.domain.usecase.ObserveFollowingParam
import com.shared.myapplication.domain.usecase.ObserveFollowingUseCase
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
import com.shared.myapplication.domain.usecase.detail.UpdateFollowingParam
import com.shared.myapplication.domain.usecase.detail.UpdateFollowingUseCase
import com.shared.myapplication.model.TvShow
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
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ShowDetailsViewModel : Store<ShowDetailViewState, ShowDetailAction, ShowDetailEffect>,
    BaseViewModel(), KoinComponent {

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

    private lateinit var tvShow: TvShow

    override fun dispatch(action: ShowDetailAction) {
        when (action) {
            is ShowDetailAction.UpdateFavorite -> {
                clientScope.launch {
                    updateFollowingUseCase(UpdateFollowingParam(tvShow.id, !action.addToWatchList))
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
                    tvShow = action.tvShow
                    val showId = tvShow.id
                    var successState = ShowDetailViewState.Success(
                        tvShow = tvShow,
                        similarShowList = persistentListOf(),
                        tvSeasonUiModels = persistentListOf(),
                        genreUIList = persistentListOf(),
                        lastAirEpList = persistentListOf(),
                    )
                    _state.value = successState
                    launch {
                        // observe follow here and update the state
                        observeFollowingUseCase(ObserveFollowingParam()).collectLatest {
                            it.onSuccess {
                                it.firstOrNull {
                                    it.id == showId
                                }?.let {
                                    tvShow = it
                                    successState = successState.copy(
                                        tvShow = it
                                    )
                                    _state.value = successState
                                }
                            }
                        }
                    }

                    // then show the rest
                    supervisorScope {
                        try {
                            val showDetailDeferred = async {
                                getShowUseCase(GetShowDetailParam(showId)).getOrThrow()
                            }
                            val seasonsDeferred = async {
                                getSeasonsUseCase(GetSeasonsParam(showId)).getOrThrow()
                            }
                            val genresDeferred = async {
                                getGenresUseCase(GetGenresParam(tvShow)).getOrThrow()
                            }
                            val episodesDeferred = async {
                                getAirEpisodesUseCase(GetAirEpisodesParam(showId)).getOrThrow()
                            }
                            val similarShowsDeferred = async {
                                getSimilarShows(GetSimilarShowsParam(showId)).getOrThrow()
                            }
                            val seasons = seasonsDeferred.await()
                            val genres = genresDeferred.await()
                            val episodes = episodesDeferred.await()
                            val similarShows = similarShowsDeferred.await()
                            val showDetail = showDetailDeferred.await()
                            Logger.e("episode $episodes")
                            _state.value = ShowDetailViewState.Success(
                                tvShow = showDetail,
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
