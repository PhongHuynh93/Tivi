package com.wind.tv.android.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.google.accompanist.insets.ProvideWindowInsets
import com.wind.tv.android.theme.TvManiacTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            ProvideWindowInsets(consumeWindowInsets = false) {
                TvManiacTheme() {
//                    SetupTheme()
//                    HomeScreen(composeNavigationFactories)
                }
            }

//            ConnectivityStatus(observeNetwork)
        }
    }
}
