package com.wind.tv.android.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
fun <T> Flow<T>.toState(initialValue: T) = produceState(initialValue = initialValue) {
    this@toState
        .distinctUntilChanged()
        .collect { value = it }
}
