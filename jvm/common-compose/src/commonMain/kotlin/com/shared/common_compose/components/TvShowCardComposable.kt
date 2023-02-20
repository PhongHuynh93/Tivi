package com.shared.common_compose.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.shared.common_compose.R
import com.shared.common_compose.theme.PreviewAppTheme

@Composable
fun TvShowCard(
    modifier: Modifier = Modifier,
    posterImageUrl: String,
    title: String,
    imageWidth: Dp = 120.dp,
    onClick: () -> Unit = {}
) {
    Card(
        elevation = 4.dp,
        modifier = modifier
            .width(imageWidth)
            .clickable { onClick() },
        shape = MaterialTheme.shapes.medium
    ) {
        NetworkImageComposable(
            imageUrl = posterImageUrl,
            contentDescription = stringResource(R.string.cd_show_poster, title),
            modifier = Modifier
                .aspectRatio(2 / 3f)
                .clip(MaterialTheme.shapes.medium),
        )
    }
}

@Preview
@Composable
fun TvShowCardPreview() {
    PreviewAppTheme {
        TvShowCard(
            posterImageUrl = "",
            title = "Lorem"
        )
    }
}
