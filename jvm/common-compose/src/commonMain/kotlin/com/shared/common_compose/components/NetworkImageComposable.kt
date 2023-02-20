package com.shared.common_compose.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import io.kamel.image.KamelImage
import io.kamel.image.lazyPainterResource

@Composable
fun NetworkImageComposable(
    imageUrl: String,
    modifier: Modifier,
    contentDescription: String
) {
    KamelImage(
        resource = lazyPainterResource(imageUrl),
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
    // we don't have ken burn effect multiplatform
    KamelImage(
        resource = lazyPainterResource(imageUrl),
        contentDescription = "",
        contentScale = ContentScale.Crop,
        modifier = modifier,
    )
//    val context = LocalContext.current
//    val kenBuns = remember { KenBurnsView(context) }
//
//    AndroidView({ kenBuns }, modifier = modifier) {
//        it.load(imageUrl)
//    }
}
