package org.opengripboard.ui.views

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material.icons.outlined.Stop
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import org.opengripboard.data.objects.GripType
import org.opengripboard.data.objects.Hangboard
import org.opengripboard.data.objects.Side
import org.opengripboard.model.OgbViewModel
import org.opengripboard.theming.Theme
import org.opengripboard.theming.spacing
import org.opengripboard.ui.components.ActionView
import org.opengripboard.ui.components.IconButton
import org.opengripboard.ui.components.ItemSlider
import org.opengripboard.ui.components.LineChart
import org.opengripboard.ui.components.SectionHeaderText
import org.opengripboard.ui.preview.ModelProvider
import kotlin.math.roundToInt

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
            listOf(GripType("full Hand", Side.Right), GripType("crimp", Side.Right)),
            GripType("full Hand", Side.Right),
            {},
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
            listOf(GripType("full Hand", Side.Right), GripType("crimp", Side.Right)),
            GripType("full Hand", Side.Right),
            {},
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
    availableGripTypes: List<GripType>,
    currentGripType: GripType,
    onGripTypeSelected: (GripType) -> Unit,
) {
    ActionView("Record a new Training", onBackPressed = { onBackPressed() }) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            if (isRecordingHangboardReadings) {
                RecordingView(currentHangboardReadings)
            } else {
                PreRecordingView(
                    onAddHangboardPressed,
                    onHangboardSelected,
                    availableHangboards,
                    currentHangboard
                )
            }
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
            val icon =
                if (isRecordingHangboardReadings) Icons.Outlined.Stop else Icons.Outlined.PlayArrow
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium)
            ) {
                availableGripTypes.forEach {
                    val colors = if (it == currentGripType) {
                        ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer,
                            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    } else {
                        ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                    Button(
                        onClick = { onGripTypeSelected(it) },
                        colors = colors
                    ) {
                        Text("${it.name} ${stringResource(it.side.display)}")
                    }
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            val onButtonPressed =
                if (isRecordingHangboardReadings) onStopRecordingPressed else onStartRecordingPressed
            IconButton(
                modifier = Modifier.size(120.dp)
                    .border(
                        1.dp,
                        MaterialTheme.colorScheme.onBackground,
                        MaterialTheme.shapes.medium
                    ),
                onButtonPressed = onButtonPressed,
                icon = icon
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))
        }
    }
}

@Composable
fun RecordingView(currentHangboardReadings: List<Int>) {
    LineChart(
        currentHangboardReadings.map { reading -> reading / 1000f },
        modifier = Modifier.padding(MaterialTheme.spacing.large)
    )
    Text(
        "Current Reading: ${
            currentHangboardReadings.lastOrNull()?.div(100f)?.roundToInt()?.div(10f) ?: "-"
        } kg",
        color = MaterialTheme.colorScheme.onBackground
    )
}

@Composable
fun PreRecordingView(
    onAddHangboardPressed: () -> Unit,
    onHangboardSelected: (Int) -> Unit,
    availableHangboards: List<Hangboard>,
    currentHangboard: Hangboard?,
) {
    SectionHeaderText("Selected Hangboard")

    ItemSlider(
        modifier = Modifier.padding(start = MaterialTheme.spacing.medium),
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
}