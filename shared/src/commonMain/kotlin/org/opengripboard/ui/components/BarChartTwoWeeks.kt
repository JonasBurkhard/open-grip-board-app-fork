package org.opengripboard.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.datetime.LocalDate
import opengripboard.shared.generated.resources.Res
import opengripboard.shared.generated.resources.today
import org.jetbrains.compose.resources.stringResource
import org.opengripboard.model.OgbViewModel
import org.opengripboard.theming.Theme
import org.opengripboard.theming.spacing
import org.opengripboard.ui.preview.ModelProvider
import kotlin.time.Duration

@Preview(name = "Light", showBackground = true)
@Composable
private fun ItemSliderLightPreview(@PreviewParameter(ModelProvider::class) model: OgbViewModel) {
    Theme(darkTheme = false) {
        BarChartTwoWeeks(
            model.statistics.trainingDurationToday,
            model.statistics.trainingDurationTwoWeekAverage,
            model.statistics.trainingDurationLastTwoWeeks
        )
    }
}

@Preview(name = "Dark", showBackground = true, backgroundColor = 0xFF121212)
@Composable
private fun ItemSliderDarkPreview(@PreviewParameter(ModelProvider::class) model: OgbViewModel) {
    Theme(darkTheme = true) {
        BarChartTwoWeeks(
            model.statistics.trainingDurationToday,
            model.statistics.trainingDurationTwoWeekAverage,
            model.statistics.trainingDurationLastTwoWeeks
        )
    }
}

@Composable
fun BarChartTwoWeeks(
    today: Duration,
    twoWeeksAvg: Duration,
    pastTwoWeeksDaily: List<Pair<LocalDate, Duration>>,
    modifier: Modifier = Modifier, onClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(120.dp)
            .border(
                1.dp,
                MaterialTheme.colorScheme.tertiary,
                MaterialTheme.shapes.large
            )
            .clip(MaterialTheme.shapes.large)
            .clickable { onClick() }
            .padding(MaterialTheme.spacing.medium)
    ) {
        val maxDuration = pastTwoWeeksDaily.maxOfOrNull { it.second.inWholeMinutes.toInt() } ?: 0
        val safeMaxDuration = if (maxDuration == 0) 1 else maxDuration
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalAlignment = Alignment.Bottom
        ) {
            pastTwoWeeksDaily.forEachIndexed { index, day ->
                val fraction = day.second.inWholeMinutes.toFloat() / safeMaxDuration.toFloat()
                val barHeight = if (fraction <= 0.05f) 0.05f else fraction
                val dayLabel = day.first.dayOfWeek.name
                    .first()
                    .uppercaseChar()
                    .toString()
                BarWithLabel(
                    dayLabel,
                    barHeight,
                    labelIsBold = (index == pastTwoWeeksDaily.lastIndex)
                )
            }
        }
        TrainingDurationText(
            today.inWholeMinutes.toInt(),
            twoWeeksAvg.inWholeMinutes.toInt()
        )
    }
}

@Composable
fun BarWithLabel(label: String, barHeight: Float = 0.05f, labelIsBold: Boolean = false) {
    Column(
        modifier = Modifier
            .width(16.dp)
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.BottomCenter) {
            Box(
                modifier = Modifier
                    .width(8.dp)
                    .fillMaxHeight(barHeight)
                    .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(2.dp))
            )
        }
        Text(
            label,
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 14.sp,
            lineHeight = 14.sp,
            fontWeight = if (labelIsBold) FontWeight.Bold else FontWeight.Normal,
            modifier = Modifier.padding(top = 2.dp)
        )
    }
}

@Composable
fun TrainingDurationText(trainingDurationToday: Int, trainingDurationTwoWeekAverage: Int) {
    Column(
        modifier = Modifier
            .width(100.dp)
            .padding(start = MaterialTheme.spacing.medium)
    ) {
        Text(
            buildAnnotatedString {
                withStyle(
                    MaterialTheme.typography.displayLarge.toSpanStyle()
                ) {
                    append(trainingDurationToday.toString())
                }
                withStyle(
                    MaterialTheme.typography.displaySmall.toSpanStyle()
                ) {
                    append(" min \n ${stringResource(Res.string.today)}")
                }
            }, lineHeight = MaterialTheme.typography.displaySmall.fontSize,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            buildAnnotatedString {
                withStyle(
                    MaterialTheme.typography.displayLarge.toSpanStyle()
                ) {
                    append(trainingDurationTwoWeekAverage.toString())
                }
                withStyle(
                    MaterialTheme.typography.displaySmall.toSpanStyle()
                ) {
                    append(" min \n 2 week avg")
                }
            },
            lineHeight = MaterialTheme.typography.displaySmall.fontSize,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}