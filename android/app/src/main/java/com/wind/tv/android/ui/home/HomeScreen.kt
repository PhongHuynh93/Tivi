package com.wind.tv.android.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.wind.tv.android.ui.detail.DetailActivity
import com.wind.tv.android.util.startActivity

@Composable
fun HomeScreen() {
    val context = LocalContext.current
    DiscoverScreen(
        openShowDetails = {
            context.startActivity<DetailActivity>(it)
        },
        moreClicked = {

        }
    )
}

