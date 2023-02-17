package com.shared.common_compose.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun TvManiacTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = if (darkTheme) DarkColors else LightColors,
        typography = TvManiacTypography,
        shapes = shapes,
        content = content
    )
}

@Composable
fun PreviewAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = if (darkTheme) DarkColors else LightColors,
        shapes = shapes,
        content = content
    )
}
