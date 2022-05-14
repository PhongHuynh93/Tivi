package com.shared.myapplication.viewmodel.home

import com.shared.myapplication.domain.usecase.ObserveDiscoverShowsInteractor
import com.shared.myapplication.model.DiscoverShowAction
import com.shared.myapplication.model.DiscoverShowAction.Error
import com.shared.myapplication.model.DiscoverShowEffect
import com.shared.myapplication.model.DiscoverShowResult
import com.shared.myapplication.model.DiscoverShowState
import com.shared.util.CoroutineScopeOwner
import com.shared.util.viewmodel.BaseViewModel
import com.shared.util.viewmodel.Store
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DiscoverViewModel(
    private val observeDiscoverShow: ObserveDiscoverShowsInteractor,
) : Store<DiscoverShowState, DiscoverShowAction, DiscoverShowEffect>,
    CoroutineScopeOwner,
    BaseViewModel() {

    override val coroutineScope: CoroutineScope
        get() = clientScope

    private val state = MutableStateFlow(DiscoverShowState(false, DiscoverShowResult.EMPTY))

    private val sideEffect = MutableSharedFlow<DiscoverShowEffect>()

    init {
        dispatch(DiscoverShowAction.LoadTvShows)
    }

    override fun observeState(): StateFlow<DiscoverShowState> = state

    override fun observeSideEffect(): Flow<DiscoverShowEffect> = sideEffect

    override fun dispatch(action: DiscoverShowAction) {
        val oldState = state.value

        when (action) {
            is DiscoverShowAction.LoadTvShows -> {
                with(state) {
                    observeDiscoverShow.execute(coroutineScope, Unit) {
                        onStart {
                            coroutineScope.launch { emit(oldState.copy(isLoading = false)) }
                        }

                        onNext {
                            coroutineScope.launch {
                                emit(
                                    oldState.copy(
                                        isLoading = false,
                                        showData = it
                                    )
                                )
                            }
                        }

                        onError {
                            coroutineScope.launch { emit(oldState.copy(isLoading = false)) }
                            dispatch(Error(it.message ?: "Something went wrong"))
                        }
                    }
                }
            }
            is Error -> {
                coroutineScope.launch {
                    sideEffect.emit(DiscoverShowEffect.Error(action.message))
                }
            }
        }
    }
}
