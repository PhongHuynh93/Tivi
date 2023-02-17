package com.shared.common_compose.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.AsyncImage
import coil.load
import coil.request.ImageRequest
import com.flaviofaria.kenburnsview.KenBurnsView

@Composable
fun NetworkImageComposable(
    imageUrl: String,
    modifier: Modifier,
    contentDescription: String
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .build(),
        contentDescription = contentDescription,
        contentScale = ContentScale.Crop,
        modifier = modifier,
        alignment = Alignment.TopStart,
    )
}

@Composable
fun KenBurnsViewImage(
    imageUrl: String,
    modifier: Modifier
) {
    val context = LocalContext.current
    val kenBuns = remember { KenBurnsView(context) }

    AndroidView({ kenBuns }, modifier = modifier) {
        it.load(imageUrl)
    }
}
