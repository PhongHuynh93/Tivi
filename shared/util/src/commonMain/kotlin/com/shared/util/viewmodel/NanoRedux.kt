package com.shared.util.viewmodel

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

open class State
interface Action
interface Effect

interface Store<S : State, A : Action, E : Effect> {
    val state: StateFlow<S>
    val effect: SharedFlow<E>
    fun dispatch(action: A)
}
