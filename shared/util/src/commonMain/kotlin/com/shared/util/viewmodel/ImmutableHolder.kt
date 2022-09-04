package com.shared.util.viewmodel

import com.shared.util.Immutable

@Immutable
class ImmutableHolder<T>(val item: T) {
    operator fun component1(): T = item
}
