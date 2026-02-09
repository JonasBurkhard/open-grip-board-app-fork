package org.opengripboard.ui.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import org.opengripboard.model.OgbViewModel
import org.opengripboard.model.PageId
import org.opengripboard.theming.MediumSpacing
import org.opengripboard.theming.Theme
import org.opengripboard.ui.components.BarChartTwoWeeks
import org.opengripboard.ui.components.SectionHeaderText
import org.opengripboard.ui.preview.ModelProvider

@Composable
@Preview(
    name = "Light",
    showBackground = true
)
@Preview(
    name = "Dark",
    showBackground = true,
    backgroundColor = 0xFF121212,
)
private fun ItemSliderPreview(@PreviewParameter(ModelProvider::class) model: OgbViewModel) {
    Theme {
        Dashboard(model)
    }
}

@Composable
fun Dashboard(model: OgbViewModel) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(MediumSpacing),
        modifier = Modifier.fillMaxWidth()
    ) {
        SectionHeaderText("Hangboards")

        SectionHeaderText("Trainings")
        BarChartTwoWeeks(
            model.statistics.trainingDurationToday,
            model.statistics.trainingDurationTwoWeekAverage,
            model.statistics.trainingDurationLastTwoWeeks,
            onClick = {model.navigation.onBarChartClick()},
            modifier = Modifier.padding(MediumSpacing, 0.dp)
        )
    }
}