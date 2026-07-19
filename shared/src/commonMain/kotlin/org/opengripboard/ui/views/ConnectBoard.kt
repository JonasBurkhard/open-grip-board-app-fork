package org.opengripboard.ui.views

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import org.opengripboard.model.OgbViewModel
import org.opengripboard.theming.Theme
import org.opengripboard.theming.spacing
import org.opengripboard.ui.components.ActionView
import org.opengripboard.ui.components.IconButton
import org.opengripboard.ui.components.QrScannerComponent
import org.opengripboard.ui.preview.ModelProvider

@Preview(name = "Light", showBackground = true)
@Composable
private fun ItemSliderLightPreview(@PreviewParameter(ModelProvider::class) model: OgbViewModel) {
    Theme(darkTheme = false) {
        ConnectBoard(
            {},
            model.hasCameraPermission,
            flashIsEnabled = false,
            onQrScannerResult = {},
            onFlashButtonPressed = {},
            onOpenSettings = {})
    }
}

@Preview(name = "Dark", showBackground = true, backgroundColor = 0xFF121212)
@Composable
private fun ItemSliderDarkPreview(@PreviewParameter(ModelProvider::class) model: OgbViewModel) {
    Theme(darkTheme = true) {
        ConnectBoard(
            {},
            model.hasCameraPermission,
            flashIsEnabled = false,
            onQrScannerResult = {},
            onFlashButtonPressed = {},
            onOpenSettings = {})
    }
}

@Composable
fun ConnectBoard(
    onBackPressed: () -> Unit,
    hasCameraPermission: Boolean,
    flashIsEnabled: Boolean,
    onQrScannerResult: (String) -> Unit,
    onFlashButtonPressed: () -> Unit,
    onOpenSettings: ()-> Unit,
) {
    ActionView("Add a new Hangboard", onBackPressed) {
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            QrScannerComponent(hasCameraPermission, flashIsEnabled, onQrScannerResult, onOpenSettings)
            Text(
                "Scan the QR Code on the front of the Hangboard you want to connect to.",
                modifier = Modifier.padding(MaterialTheme.spacing.large),
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(Modifier.weight(1f))
            IconButton(
                modifier = Modifier.size(120.dp).border(
                    1.dp,
                    MaterialTheme.colorScheme.onBackground,
                    MaterialTheme.shapes.medium
                ),
                onButtonPressed = { onFlashButtonPressed() },
                icon = Icons.Filled.FlashOn
            )
            Spacer(Modifier.weight(1f))
        }
    }
}