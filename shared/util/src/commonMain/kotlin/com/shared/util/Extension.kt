package com.shared.util

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

suspend fun <A, B> Iterable<A>.mapAsync(f: suspend (A) -> B): List<B> =
    coroutineScope { map { async { f(it) } }.awaitAll() }

fun <T> Flow<T>.toResult() = this.map {
    Result.success(it)
}
