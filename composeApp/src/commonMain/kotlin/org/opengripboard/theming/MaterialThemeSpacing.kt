package org.opengripboard.theming

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

val MaterialTheme.spacing: Spacing
    @Composable get() = LocalSpacing.current