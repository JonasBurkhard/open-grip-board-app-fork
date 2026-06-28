package org.opengripboard.ui.components

import androidx.compose.runtime.Composable


@Composable
actual fun QrScannerComponent(
    hasCameraPermission: Boolean,
    flashIsEnabled: Boolean,
    onQrScannerResult: (String) -> Unit,
    onOpenSettings:()->Unit
) {
}
