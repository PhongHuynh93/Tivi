package com.shared.myapplication.viewmodel.following

import com.shared.util.Immutable
import com.shared.myapplication.model.TvShow
import com.shared.util.viewmodel.Action
import com.shared.util.viewmodel.Effect
import com.shared.util.viewmodel.State

@Immutable
sealed class WatchlistState() : State() {
    object InProgress : WatchlistState()
    class Success(val data: List<TvShow>) : WatchlistState()
}

sealed class WatchlistAction : Action {
    object LoadWatchlist : WatchlistAction()
    data class Error(val message: String = "") : WatchlistAction()
}

sealed class WatchlistEffect : Effect {
    data class Error(val message: String = "") : WatchlistEffect()
}
