package com.wind.tv.android.ui.showdetails

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shared.common_compose.components.ColumnSpacer
import com.shared.common_compose.components.TvShowCard
import com.shared.common_compose.theme.TvManiacTheme
import com.shared.myapplication.model.TvShow
import com.wind.tv.android.R
import com.wind.tv.android.util.showList
import dev.chrisbanes.snapper.ExperimentalSnapperApi
import dev.chrisbanes.snapper.rememberSnapperFlingBehavior
import kotlinx.collections.immutable.ImmutableList

@OptIn(ExperimentalSnapperApi::class)
@Composable
fun SimilarShowsShowsContent(
    similarShows: ImmutableList<TvShow>,
    onShowClicked: (TvShow) -> Unit = {}
) {
    val lazyListState = rememberLazyListState()

    if (similarShows.isNotEmpty()) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {

            ColumnSpacer(8)

            Text(
                text = stringResource(id = R.string.title_similar),
                style = MaterialTheme.typography.h6,
            )

            ColumnSpacer(12)

            LazyRow(
                state = lazyListState,
                flingBehavior = rememberSnapperFlingBehavior(lazyListState),
            ) {
                itemsIndexed(similarShows) { _, tvShow ->
                    TvShowCard(
                        posterImageUrl = tvShow.posterImageUrl,
                        title = tvShow.title,
                        onClick = { onShowClicked(tvShow) },
                        imageWidth = 84.dp,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                }
            }

            ColumnSpacer(value = 16)
        }
    }
}

@Preview("default")
@Preview("dark theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SimilarShowsShowsContentPreview() {
    TvManiacTheme {
        Surface {
            SimilarShowsShowsContent(
                similarShows = showList,
            )
        }
    }
}
