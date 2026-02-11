package org.opengripboard.ui.views

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import org.opengripboard.data.objects.Training
import org.opengripboard.model.OgbViewModel
import org.opengripboard.theming.MediumSpacing
import org.opengripboard.theming.Theme
import org.opengripboard.ui.components.ActionView
import org.opengripboard.ui.components.IconButton
import org.opengripboard.ui.components.LineChart
import org.opengripboard.ui.preview.ModelProvider
import kotlin.math.round

@Preview(name = "Light", showBackground = true)
@Composable
private fun ItemSliderLightPreview(@PreviewParameter(ModelProvider::class) model: OgbViewModel) {
    Theme(darkTheme = false) {
        PastTrainings(model.pastTrainings, {}, {}, {})
    }
}

@Preview(name = "Dark", showBackground = true, backgroundColor = 0xFF121212)
@Composable
private fun ItemSliderDarkPreview(@PreviewParameter(ModelProvider::class) model: OgbViewModel) {
    Theme(darkTheme = true) {
        PastTrainings(model.pastTrainings, {}, {}, {})
    }
}

@Composable
fun PastTrainings(
    pastTrainings: List<Training>,
    onNewRecordingPressed: () -> Unit,
    onDeletePressed: (Int) -> Unit,
    onBackPressed: () -> Unit
) {
    ActionView("Your recorded trainings", onBackPressed) {
        Text("training")
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(MediumSpacing, 0.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(pastTrainings.size) { index ->
                    TrainingEntry(pastTrainings[index], onDeletePressed)
                }
            }
            Spacer(modifier = Modifier.height(30.dp))
            IconButton(
                Icons.Filled.Add, onNewRecordingPressed,
                modifier = Modifier.size(60.dp)
                    .border(
                        1.dp, MaterialTheme.colorScheme.onBackground,
                        MaterialTheme.shapes.medium
                    )
            )
            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Composable
private fun TrainingEntry(training: Training, onDeletePressed: (Int) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .border(1.dp, MaterialTheme.colorScheme.tertiary, MaterialTheme.shapes.medium)
            .padding(MediumSpacing),
        verticalAlignment = Alignment.CenterVertically
    ) {
        LineChart(
            training.dataPoints.map { point -> point / 1000f },
            modifier = Modifier.width(80.dp)
        )
        Spacer(modifier = Modifier.width(MediumSpacing))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                "Date: ${training.date.date}",
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                "Duration: ${training.duration}",
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                "Max Force: ${round(training.dataPoints.max() / 10f.toDouble()) / 100} kg",
                color = MaterialTheme.colorScheme.onBackground
            )
        }
        Spacer(modifier = Modifier.width(MediumSpacing))
        IconButton(Icons.Outlined.Delete, { onDeletePressed(training.id) })
    }
}