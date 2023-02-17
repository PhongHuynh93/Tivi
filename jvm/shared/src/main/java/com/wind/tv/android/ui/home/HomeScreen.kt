package com.wind.tv.android.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.shared.myapplication.model.TvShow
import com.wind.tv.android.ui.detail.DetailActivity
import com.wind.tv.android.util.startActivity

@Composable
fun HomeScreen(
    openShowDetails: (show: TvShow) -> Unit,
    moreClicked: (showType: Int) -> Unit,
) {
    DiscoverScreen(
        openShowDetails,
        moreClicked
    )
}
