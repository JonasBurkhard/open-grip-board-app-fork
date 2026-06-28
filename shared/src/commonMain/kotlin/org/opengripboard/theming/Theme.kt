package org.opengripboard.theming

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider


@Composable
fun Theme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colors,
        typography = appTypography(),
        shapes = Shapes,
    ) {
        CompositionLocalProvider(
            LocalSpacing provides Spacing()
        ) {
            content()
        }
    }
}