package org.opengripboard.ui.views

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import org.opengripboard.data.objects.Hangboard
import org.opengripboard.model.OgbViewModel
import org.opengripboard.theming.LargeSpacing
import org.opengripboard.theming.MediumSpacing
import org.opengripboard.theming.Theme
import org.opengripboard.ui.components.ActionView
import org.opengripboard.ui.components.IconButton
import org.opengripboard.ui.components.ItemSlider
import org.opengripboard.ui.components.LineChart
import org.opengripboard.ui.preview.ModelProvider

@Preview(name = "Light", showBackground = true)
@Composable
private fun ItemSliderLightPreview(@PreviewParameter(ModelProvider::class) model: OgbViewModel) {
    Theme(darkTheme = false) {
        RecordingData(
            { },
            model.hangboards.isRecording,
            model.hangboards.currentReadings,
            {},
            {},
            {},
            {},
            model.hangboards.availableHangboards,
            model.hangboards.currentHangboard,
        )
    }
}

@Preview(name = "Dark", showBackground = true, backgroundColor = 0xFF121212)
@Composable
private fun ItemSliderDarkPreview(@PreviewParameter(ModelProvider::class) model: OgbViewModel) {
    Theme(darkTheme = true) {
        RecordingData(
            { },
            model.hangboards.isRecording,
            model.hangboards.currentReadings,
            {},
            {},
            {},
            {},
            model.hangboards.availableHangboards,
            model.hangboards.currentHangboard,
        )
    }
}


@Composable
fun RecordingData(
    onBackPressed: () -> Unit,
    isRecordingHangboardReadings: Boolean,
    currentHangboardReadings: List<Int>,
    onStartRecordingPressed: () -> Unit,
    onStopRecordingPressed: () -> Unit,
    onAddHangboardPressed: () -> Unit,
    onHangboardSelected: (Int) -> Unit,
    availableHangboards: List<Hangboard>,
    currentHangboard: Hangboard?,
) {
    ActionView("Record a new Training", onBackPressed = { onBackPressed() }) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            if (isRecordingHangboardReadings) {
                RecordingView(currentHangboardReadings, onStopRecordingPressed)
            } else {
                PreRecordingView(
                    onStartRecordingPressed,
                    onAddHangboardPressed,
                    onHangboardSelected,
                    availableHangboards,
                    currentHangboard
                )
            }
        }
    }
}

@Composable
fun RecordingView(currentHangboardReadings: List<Int>, onStopRecordingPressed: () -> Unit) {
    LineChart(
        currentHangboardReadings.map { reading -> reading / 1000f },
        modifier = Modifier.padding(LargeSpacing)
    )
    Text(
        "Current Reading: ${currentHangboardReadings.firstOrNull() ?: "-"}",
        color = MaterialTheme.colorScheme.onBackground
    )
    IconButton(
        modifier = Modifier.size(120.dp),
        onButtonPressed = { onStopRecordingPressed() },
        icon = Icons.Filled.PlayArrow
    )
}

@Composable
fun PreRecordingView(
    onStartRecordingPressed: () -> Unit,
    onAddHangboardPressed: () -> Unit,
    onHangboardSelected: (Int) -> Unit,
    availableHangboards: List<Hangboard>,
    currentHangboard: Hangboard?,
) {
    Text(
        "Selected Hangboard",
        color = MaterialTheme.colorScheme.onBackground,
        modifier = Modifier.padding(start = MediumSpacing)
    )

    ItemSlider(
        modifier = Modifier.padding(start = MediumSpacing),
        onAddPressed = { onAddHangboardPressed() },
        onItemSelected = { id -> onHangboardSelected(id) },
    )
    {
        availableHangboards.map { board ->
            @Composable {
                val color = currentHangboard?.hangboardId.let {
                    if (board.hangboardId == it) {
                        Color.Green
                    } else {
                        MaterialTheme.colorScheme.onPrimaryContainer
                    }
                }
                Text(
                    board.name,
                    color = color
                )
            }
        }
    }
    Spacer(modifier = Modifier.height(20.dp))
    IconButton(
        modifier = Modifier.size(120.dp)
            .border(1.dp, MaterialTheme.colorScheme.onBackground, MaterialTheme.shapes.medium),
        onButtonPressed = { onStartRecordingPressed() },
        icon = Icons.Filled.Stop
    )
}