package com.shared.util.network

import com.shared.util.AppContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

actual class ObserveConnectionState actual constructor(context: AppContext) {
    actual fun observeConnectivityAsFlow(): Flow<ConnectionState> {
        // TODO:: ADD Connection State implementation.
        return flowOf(ConnectionState.ConnectionAvailable)
    }
}