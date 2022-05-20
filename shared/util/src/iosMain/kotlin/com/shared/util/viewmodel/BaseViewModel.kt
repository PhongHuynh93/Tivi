package com.shared.util.viewmodel

import io.ktor.utils.io.core.Closeable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

actual abstract class BaseViewModel actual constructor() {

    private val viewModelJob = SupervisorJob()

    private val viewModelScope: CoroutineScope = CoroutineScope(
        Dispatchers.Main + viewModelJob
    )

    protected actual val clientScope: CoroutineScope = viewModelScope

    fun <T> Flow<T>.observe(onChange: ((T) -> Unit)): Closeable {
        val job = Job()
        onEach {
            onChange(it)
        }.launchIn(
            CoroutineScope(Dispatchers.Main + job)
        )
        return object : Closeable {
            override fun close() {
                job.cancel()
            }
        }
    }

    actual abstract fun attach()

    protected actual fun detach() {
        viewModelJob.cancelChildren()
    }

    actual abstract val state: StateFlow<State>
    actual abstract val effect: SharedFlow<Effect>
}
