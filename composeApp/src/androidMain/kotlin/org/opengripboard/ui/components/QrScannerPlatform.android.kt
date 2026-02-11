package org.opengripboard.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.unit.dp
import ch.ubique.qrscanner.compose.QrScanner
import ch.ubique.qrscanner.mlkit.decoder.MLKitImageDecoder
import ch.ubique.qrscanner.scanner.BarcodeFormat
import ch.ubique.qrscanner.scanner.ScanningMode
import ch.ubique.qrscanner.state.DecodingState
import org.opengripboard.theming.LargeSpacing

@Composable
actual fun QrScannerComponent(
    hasCameraPermission: Boolean,
    flashIsEnabled: Boolean,
    onQrScannerResult: (String) -> Unit
) {
    if (hasCameraPermission && !LocalInspectionMode.current) {
        val formats = listOf(BarcodeFormat.QR_CODE, BarcodeFormat.CODE_128)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp)
                .padding(LargeSpacing)
                .clipToBounds()
                .clip(MaterialTheme.shapes.large),
            contentAlignment = Alignment.Center
        ) {
            val decoder = remember { MLKitImageDecoder(formats) }
            QrScanner(
                imageDecoders = listOf(decoder),
                scannerCallback = { state ->
                    when (state) {
                        is DecodingState.NotFound -> onQrScannerResult("notFound")
                        is DecodingState.Decoded -> onQrScannerResult(state.content)
                        is DecodingState.Error -> onQrScannerResult("error")
                    }
                },
                modifier = Modifier.fillMaxSize(),
                scanningMode = ScanningMode.PARALLEL,
                isFlashEnabled = remember { mutableStateOf(flashIsEnabled) },
                linearZoom = remember { mutableStateOf(0f) },
            )
        }
    } else if (!hasCameraPermission) {
        Text("Camera permission required")
    } else {
        // Preview fallback
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp)
                .clip(MaterialTheme.shapes.large)
                .border(
                    2.dp,
                    MaterialTheme.colorScheme.onBackground,
                    MaterialTheme.shapes.large
                )
                .padding(LargeSpacing),
            contentAlignment = Alignment.Center
        ) {
            Text("QR Scanner Preview", color = MaterialTheme.colorScheme.onBackground)
        }
    }
}
