package com.shared.myapplication.viewmodel.home

import com.shared.ksp_annotation.KMPViewModel
import com.shared.myapplication.domain.usecase.show.GetPopularShowsParam
import com.shared.myapplication.domain.usecase.show.GetPopularShowsUseCase
import com.shared.myapplication.domain.usecase.show.GetTopRatedShowsParam
import com.shared.myapplication.domain.usecase.show.GetTopRatedShowsUseCase
import com.shared.myapplication.domain.usecase.show.GetTrendingShowsParam
import com.shared.myapplication.domain.usecase.show.GetTrendingShowsUseCase
import com.shared.myapplication.model.ShowCategory
import com.shared.myapplication.model.TvShow
import com.shared.myapplication.viewmodel.DiscoverShowState
import com.shared.myapplication.viewmodel.home.DiscoverShowAction.Error
import com.shared.util.getErrorMessage
import com.shared.util.viewmodel.BaseViewModel
import com.shared.util.viewmodel.Store
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

private const val FEATURED_LIST_SIZE = 5

@KMPViewModel
class DiscoverViewModel :
    BaseViewModel(),
    Store<DiscoverShowState, DiscoverShowAction, DiscoverShowEffect>,
    KoinComponent {

    private val getPopularShows: GetPopularShowsUseCase by inject()
    private val getTopRatedShows: GetTopRatedShowsUseCase by inject()
    private val getTrendingShows: GetTrendingShowsUseCase by inject()

    private val _state = MutableStateFlow<DiscoverShowState>(DiscoverShowState.InProgress)

    override val state: StateFlow<DiscoverShowState> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<DiscoverShowEffect>()

    override val effect: SharedFlow<DiscoverShowEffect> = _effect.asSharedFlow()

    init {
        dispatch(DiscoverShowAction.LoadTvShows)
    }

    override fun dispatch(action: DiscoverShowAction) {
        clientScope.launch {
            when (action) {
                is DiscoverShowAction.LoadTvShows -> {
                    supervisorScope {
                        try {
                            val popularDeferred = async {
                                getPopularShows(GetPopularShowsParam()).getOrThrow()
                            }
                            val trendingDeferred = async {
                                getTrendingShows(GetTrendingShowsParam()).getOrThrow()
                            }
                            val topRatedDeferred = async {
                                getTopRatedShows(GetTopRatedShowsParam()).getOrThrow()
                            }
                            val popular = popularDeferred.await()
                            val trending = trendingDeferred.await()
                            val topRated = topRatedDeferred.await()
                            _state.value = DiscoverShowState.Success(
                                data = DiscoverShow(
                                    featuredShows = DiscoverShow.DiscoverShowsData(
                                        category = ShowCategory.TRENDING,
                                        tvShows = trending
                                            .sortedBy { it.votes }
                                            .take(FEATURED_LIST_SIZE)
                                            .toImmutableList()
                                    ),
                                    trendingShows = trending.toShowData(ShowCategory.TRENDING),
                                    popularShows = popular.toShowData(ShowCategory.POPULAR),
                                    topRatedShows = topRated.toShowData(ShowCategory.TOP_RATED)
                                )
                            )
                        } catch (e: Exception) {
                            dispatch(Error(e.getErrorMessage()))
                        }
                    }
                }
                is Error -> {
                    _effect.emit(DiscoverShowEffect.Error(action.message))
                }
            }
        }
    }

    private fun List<TvShow>.toShowData(category: ShowCategory) = let {
        DiscoverShow.DiscoverShowsData(
            category = category,
            tvShows = toImmutableList()
        )
    }
}
