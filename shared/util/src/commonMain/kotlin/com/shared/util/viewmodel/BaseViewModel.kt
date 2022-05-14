package com.shared.util.viewmodel

import kotlinx.coroutines.CoroutineScope

@Suppress("EmptyDefaultConstructor")
expect open class BaseViewModel() {
    protected val clientScope: CoroutineScope
    protected open fun onCleared()
}
