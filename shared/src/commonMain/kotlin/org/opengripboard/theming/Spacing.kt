package org.opengripboard.theming

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp

data class Spacing(
    val small: androidx.compose.ui.unit.Dp = 4.dp,
    val medium: androidx.compose.ui.unit.Dp = 8.dp,
    val large: androidx.compose.ui.unit.Dp = 16.dp
)


val LocalSpacing = staticCompositionLocalOf { Spacing() }