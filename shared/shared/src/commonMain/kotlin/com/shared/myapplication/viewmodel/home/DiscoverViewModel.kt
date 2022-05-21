package com.shared.myapplication.viewmodel.home

import com.shared.myapplication.domain.usecase.ObserveDiscoverShowsInteractor
import com.shared.myapplication.viewmodel.home.DiscoverShowAction.Error
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

class DiscoverViewModel(
) : BaseViewModel(), Store<DiscoverShowState, DiscoverShowAction, DiscoverShowEffect>,
    KoinComponent {

    private val observeDiscoverShow: ObserveDiscoverShowsInteractor by inject()

    private val _state = MutableStateFlow<DiscoverShowState>(DiscoverShowState.InProgress)

    override val state: StateFlow<DiscoverShowState> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<DiscoverShowEffect>()

    override val effect: SharedFlow<DiscoverShowEffect> = _effect.asSharedFlow()

    init {
        attach()
    }

    override fun attach() {
        dispatch(DiscoverShowAction.LoadTvShows)
    }

    override fun dispatch(action: DiscoverShowAction) {

        when (action) {
            is DiscoverShowAction.LoadTvShows -> {
                observeDiscoverShow.execute(clientScope, Unit) {

                    onNext {
                        clientScope.launch {
                            _state.value = DiscoverShowState.Success(
                                data = it
                            )
                        }
                    }

                    onError {
                        dispatch(Error(it.message ?: "Something went wrong"))
                    }
                }
            }
            is Error -> {
                clientScope.launch {
                    _effect.emit(DiscoverShowEffect.Error(action.message))
                }
            }
        }
    }
}
