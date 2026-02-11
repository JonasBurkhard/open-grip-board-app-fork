package org.opengripboard.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import org.opengripboard.model.OgbViewModel
import org.opengripboard.theming.Theme
import org.opengripboard.ui.preview.ModelProvider

@Preview(name = "Light", showBackground = true)
@Composable
private fun ItemSliderLightPreview(@PreviewParameter(ModelProvider::class) model: OgbViewModel) {
    Theme(darkTheme = false) {
        QrScannerComponent(true,mutableStateOf(false),{})
    }
}

@Preview(name = "Dark", showBackground = true, backgroundColor = 0xFF121212)
@Composable
private fun ItemSliderDarkPreview(@PreviewParameter(ModelProvider::class) model: OgbViewModel) {
    Theme(darkTheme = true) {
        QrScannerComponent(true,mutableStateOf(false),{})
    }
}

@Composable
expect fun QrScannerComponent(hasCameraPermission: Boolean, flashIsEnabled: State<Boolean>, onQrScannerResult: (String)->Unit)