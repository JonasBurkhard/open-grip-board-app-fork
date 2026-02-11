package org.opengripboard

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import org.opengripboard.model.OgbViewModel
import org.opengripboard.ui.App

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    ComposeViewport {
        App(OgbViewModel())
    }
}