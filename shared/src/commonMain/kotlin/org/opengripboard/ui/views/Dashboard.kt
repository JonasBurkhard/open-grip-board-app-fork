package org.opengripboard.ui.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import opengripboard.shared.generated.resources.Res
import opengripboard.shared.generated.resources.hangboard
import opengripboard.shared.generated.resources.opengripboard
import org.jetbrains.compose.resources.stringResource
import org.opengripboard.model.OgbViewModel
import org.opengripboard.theming.Theme
import org.opengripboard.theming.spacing
import org.opengripboard.ui.components.BarChartTwoWeeks
import org.opengripboard.ui.components.IconButton
import org.opengripboard.ui.components.ItemSlider
import org.opengripboard.ui.components.SectionHeaderText
import org.opengripboard.ui.preview.ModelProvider

@Preview(name = "Light", showBackground = true)
@Composable
private fun ItemSliderLightPreview(@PreviewParameter(ModelProvider::class) model: OgbViewModel) {
    Theme(darkTheme = false) {
        Dashboard(model)
    }
}

@Preview(name = "Dark", showBackground = true, backgroundColor = 0xFF121212)
@Composable
private fun ItemSliderDarkPreview(@PreviewParameter(ModelProvider::class) model: OgbViewModel) {
    Theme(darkTheme = true) {
        Dashboard(model)
    }
}

@Composable
fun Dashboard(model: OgbViewModel) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row{
            Text(stringResource(Res.string.opengripboard),         modifier = Modifier
                .padding(MaterialTheme.spacing.medium)
                .weight(1f),
                textAlign = TextAlign.Start,
                color = MaterialTheme.colorScheme.onBackground)
            IconButton(Icons.Filled.Settings, { model.dashboardModel.onLocalAppSettingsSelected() })
        }
        SectionHeaderText(stringResource(Res.string.hangboard))
        ItemSlider(
            { model.onAddHangboard() },
            { index -> model.onHangboardSelected(index) },
            modifier = Modifier.padding(start = MaterialTheme.spacing.medium)
        ) {
            model.hangboards.availableHangboards.map { board ->
                @Composable {
                    val color = model.hangboards.currentHangboard?.hangboardId.let {
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

        SectionHeaderText("Trainings")
        BarChartTwoWeeks(
            model.statistics.trainingDurationToday,
            model.statistics.trainingDurationTwoWeekAverage,
            model.statistics.trainingDurationLastTwoWeeks,
            onClick = { model.navigation.onBarChartClick() },
            modifier = Modifier.padding(MaterialTheme.spacing.medium, 0.dp)
        )
    }
}