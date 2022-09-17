package com.shared.util.viewmodel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

expect abstract class BaseViewModel() {

    abstract val state: StateFlow<State>

    abstract val effect: SharedFlow<Effect>

    protected val clientScope: CoroutineScope

    protected fun detach()
}
