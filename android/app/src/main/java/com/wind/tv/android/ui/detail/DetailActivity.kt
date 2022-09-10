package com.wind.tv.android.ui.detail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import co.touchlab.kermit.Logger
import com.google.accompanist.insets.ProvideWindowInsets
import com.shared.common_compose.theme.TvManiacTheme

class DetailActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            ProvideWindowInsets(consumeWindowInsets = false) {
                TvManiacTheme() {
                    DetailScreen(
                        navigateUp = {
                            finish()
                        },
                        onShowClicked = { showId ->
                            Logger.d(showId)
                        },
                        onSeasonClicked = { showId, seasonName ->
                            Logger.d("$showId $seasonName")
                        },
                        onEpisodeClicked = { episodeNumber, seasonNumber ->
                            // Navigate to episode detail screen
                            Logger.d("$episodeNumber $seasonNumber")
                        }
                    )
                }
            }
        }
    }
}
