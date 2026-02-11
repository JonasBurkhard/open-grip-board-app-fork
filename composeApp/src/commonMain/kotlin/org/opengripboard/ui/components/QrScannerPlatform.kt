package org.opengripboard.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import org.opengripboard.theming.Theme

@Preview(name = "Light", showBackground = true)
@Composable
private fun ItemSliderLightPreview() {
    Theme(darkTheme = false) {
        QrScannerComponent(
            hasCameraPermission = true,
            flashIsEnabled = false,
            onQrScannerResult = {})
    }
}

@Preview(name = "Dark", showBackground = true, backgroundColor = 0xFF121212)
@Composable
private fun ItemSliderDarkPreview() {
    Theme(darkTheme = true) {
        QrScannerComponent(
            hasCameraPermission = true,
            flashIsEnabled = false,
            onQrScannerResult = {})
    }
}

@Composable
expect fun QrScannerComponent(
    hasCameraPermission: Boolean,
    flashIsEnabled: Boolean,
    onQrScannerResult: (String) -> Unit
)