package org.opengripboard

import androidx.compose.ui.window.ComposeUIViewController
import org.opengripboard.model.OgbViewModel
import org.opengripboard.ui.App

fun MainViewController() = ComposeUIViewController { App(OgbViewModel()) }