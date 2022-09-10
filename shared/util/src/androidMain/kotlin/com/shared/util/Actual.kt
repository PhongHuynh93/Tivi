package com.shared.util

import android.os.Parcelable
import com.shared.util.viewmodel.BaseViewModel
import kotlinx.parcelize.Parcelize
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.definition.Definition
import org.koin.core.instance.InstanceFactory
import org.koin.core.module.Module
import org.koin.core.qualifier.Qualifier

actual val platform = PlatformType.ANDROID

actual val platformCoroutineDispatcher: CoroutineDispatcher = Dispatchers.IO

actual fun isDebug() = BuildConfig.DEBUG

actual typealias Parcelize = Parcelize

actual typealias Parcelable = Parcelable

actual inline fun <reified T : BaseViewModel> Module.viewModelDefinition(
    qualifier: Qualifier?,
    createdAtStart: Boolean,
    noinline definition: Definition<T>
): Pair<Module, InstanceFactory<T>> = viewModel(
    qualifier = qualifier,
    definition = definition
)

actual typealias Immutable = androidx.compose.runtime.Immutable
