package com.wind.tv.android.ui.detail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.google.accompanist.insets.ProvideWindowInsets
import com.shared.common_compose.theme.TvManiacTheme

class DetailActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            ProvideWindowInsets(consumeWindowInsets = false) {
                TvManiacTheme() {
                    DetailScreen(
                        navigateUp = {  },
                        onShowClicked = { showId ->
                        },
                        onSeasonClicked = { showId, seasonName ->
                        },
                        onEpisodeClicked = { episodeNumber, seasonNumber ->
                            // Navigate to episode detail screen
                        }
                    )
                }
            }
        }
    }
}
