package org.opengripboard.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State


@Composable
actual fun QrScannerComponent(
    hasCameraPermission: Boolean,
    flashIsEnabled: State<Boolean>,
    onQrScannerResult: (String) -> Unit
) {
}
