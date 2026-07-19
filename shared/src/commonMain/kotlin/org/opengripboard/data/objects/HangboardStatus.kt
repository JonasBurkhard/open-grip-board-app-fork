package org.opengripboard.data.objects

import androidx.compose.ui.graphics.Color
import kotlinx.serialization.Serializable

@Serializable
enum class HangboardStatus(val color: Color) {
    Online(Color.Green),
    Offline(Color.Red),
}