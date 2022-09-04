package com.wind.tv.android.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.core.view.WindowCompat
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.shared.common_compose.components.ConnectionStatus
import com.shared.common_compose.theme.TvManiacTheme
import com.shared.util.network.ConnectionState
import com.shared.util.network.ObserveConnectionState
import com.wind.tv.android.ui.home.HomeScreen
import com.wind.tv.android.util.toState
import kotlinx.coroutines.delay
import org.koin.androidx.compose.get

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            ProvideWindowInsets(consumeWindowInsets = false) {
                TvManiacTheme() {
                    SetupTheme()
                    HomeScreen()
                }
                ConnectivityStatus(get())
            }
        }
    }
}

@Composable
private fun SetupTheme() {
    val systemUiController = rememberSystemUiController()
    val isLightTheme = !isSystemInDarkTheme()

    val systemBarColor = MaterialTheme.colors.surface.copy(alpha = 0.0f)
    val transparentColor: (Color) -> Color = { original ->
        systemBarColor.compositeOver(original)
    }
    SideEffect {
        systemUiController.setSystemBarsColor(
            color = Color.Transparent,
            darkIcons = true
        )

//        systemUiController.setStatusBarColor(
//            color = Color.Transparent,
//            darkIcons = isLightTheme,
//            transformColorForLightContent = transparentColor
//        )
//
//        systemUiController.setNavigationBarColor(
//            color = if (isLightTheme) LightColors.surface else DarkColors.primary,
//            darkIcons = isLightTheme,
//            navigationBarContrastEnforced = false,
//            transformColorForLightContent = transparentColor
//        )
    }
}

@Composable
fun connectivityState(observeNetwork: ObserveConnectionState): State<ConnectionState> {
    return observeNetwork.observeConnectivityAsFlow()
        .toState(initialValue = observeNetwork.currentConnectivityState)
}

@Composable
fun ConnectivityStatus(observeNetwork: ObserveConnectionState) {
    val connection by connectivityState(observeNetwork)
    val isConnected = connection == ConnectionState.ConnectionAvailable

    var visibility by remember { mutableStateOf(false) }

    LaunchedEffect(isConnected) {
        visibility = if (!isConnected) {
            true
        } else {
            delay(2000)
            false
        }
    }

    AnimatedVisibility(
        visible = visibility,
        enter = expandVertically(),
        exit = shrinkVertically()
    ) {
        ConnectionStatus(isConnected = isConnected)
    }
}
