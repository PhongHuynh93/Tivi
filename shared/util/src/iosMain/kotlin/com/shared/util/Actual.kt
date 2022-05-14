package com.shared.myapplication

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

actual val platform = PlatformType.IOS

actual val platformCoroutineDispatcher: CoroutineDispatcher = Dispatchers.Default

actual fun isDebug() = Platform.isDebugBinary

actual interface Parcelable

actual inline fun <reified T : BaseViewModel> Module.viewModelDefinition(
    qualifier: Qualifier?,
    createdAtStart: Boolean,
    noinline definition: Definition<T>
): Pair<Module, InstanceFactory<T>> = single(
    qualifier = qualifier,
    createdAtStart = createdAtStart,
    definition = definition
)
