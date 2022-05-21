package com.shared.myapplication.viewmodel.following

import com.shared.myapplication.domain.usecase.ObserveFollowingInteractor
import com.shared.myapplication.viewmodel.home.DiscoverShowEffect
import com.shared.util.viewmodel.BaseViewModel
import com.shared.util.viewmodel.Store
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class FollowingViewModel(
) : BaseViewModel(), Store<WatchlistState, WatchlistAction, WatchlistEffect>, KoinComponent {
    private val _state = MutableStateFlow<WatchlistState>(WatchlistState.InProgress)
    override val state: StateFlow<WatchlistState> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<WatchlistEffect>()
    override val effect: SharedFlow<WatchlistEffect> = _effect.asSharedFlow()

    private val interactor: ObserveFollowingInteractor by inject()

    init {
        attach()
    }

    override fun attach() {
        dispatch(WatchlistAction.LoadWatchlist)
    }

    override fun dispatch(action: WatchlistAction) {
        when (action) {
            is WatchlistAction.Error -> {
                clientScope.launch {
                    _effect.emit(WatchlistEffect.Error(action.message))
                }
            }
            WatchlistAction.LoadWatchlist -> {
                interactor.execute(clientScope, Unit) {

                    onNext {
                        clientScope.launch {
                            _state.value = WatchlistState.Success(it)
                        }
                    }

                    onError {
                        dispatch(WatchlistAction.Error(it.message ?: "Something went wrong"))
                    }
                }
            }
        }
    }
}
