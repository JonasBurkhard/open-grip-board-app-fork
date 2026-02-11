package org.opengripboard.data.objects

import androidx.compose.ui.graphics.Color

enum class HangboardStatus(val color: Color) {
    Online(Color.Green),
    Offline(Color.Red),
}