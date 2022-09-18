package com.shared.myapplication.viewmodel

import com.shared.ksp_annotation.uiState.Extensive
import com.shared.ksp_annotation.uiState.ExtensiveModel
import com.shared.ksp_annotation.uiState.ExtensiveSealed
import com.shared.myapplication.viewmodel.home.DiscoverShow

@ExtensiveSealed(
    models = [
        ExtensiveModel(DiscoverShow::class),
    ]
)
sealed class State : com.shared.util.viewmodel.State() {
    class Success(val data: Extensive) : State()
    object InProgress : State()
    class Error(val message: String) : State()
}
