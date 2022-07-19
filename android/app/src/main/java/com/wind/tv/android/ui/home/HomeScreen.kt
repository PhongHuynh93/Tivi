package com.wind.tv.android.ui.home

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.wind.tv.android.ui.detail.DetailActivity

@Composable
fun HomeScreen() {
    val context = LocalContext.current
    DiscoverScreen(
        openShowDetails = {
            val intent = Intent(context, DetailActivity::class.java)
            context.startActivity(intent)
        },
        moreClicked = {

        }
    )
}

