package com.shared.common_compose.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.shared.common_compose.R
import com.shared.common_compose.theme.PreviewAppTheme


@Composable
fun TvShowCard(
    modifier: Modifier = Modifier,
    posterImageUrl: String,
    title: String,
    isFirstCard: Boolean = false,
    imageWidth: Dp = 120.dp,
    rowSpacer: Int = 4,
    onClick: () -> Unit = {}
) {
    RowSpacer(value = if (isFirstCard) 16 else 4)

    Column(
        modifier = modifier
            .width(imageWidth)
            .padding(vertical = 8.dp)
    ) {
        Card(
            elevation = 4.dp,
            modifier = Modifier.clickable { onClick() },
            shape = MaterialTheme.shapes.medium
        ) {
            NetworkImageComposable(
                imageUrl = posterImageUrl,
                contentDescription = stringResource(R.string.cd_show_poster, title),
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(2 / 3f)
                    .clip(MaterialTheme.shapes.medium),
            )
        }

        Spacer(Modifier.height(8.dp))
    }

    RowSpacer(value = rowSpacer)
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
