package com.shared.util.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

actual abstract class BaseViewModel actual constructor() : ViewModel() {

    protected actual val clientScope: CoroutineScope = viewModelScope

    actual abstract fun attach()

    protected actual fun detach() {
    }

    actual abstract val state: StateFlow<State>
    actual abstract val effect: SharedFlow<Effect>
}
