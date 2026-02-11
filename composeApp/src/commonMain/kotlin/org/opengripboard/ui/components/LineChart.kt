package org.opengripboard.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.tooling.preview.Preview
import org.opengripboard.theming.Theme

@Preview(name = "Light", showBackground = true)
@Composable
private fun ItemSliderLightPreview() {
    Theme(darkTheme = false) {
        LineChart(listOf(1f, 3f, 4f, 8f, 10f, 11f, 10f, 11f, 9f, 8f, 11f))
    }
}

@Preview(name = "Dark", showBackground = true, backgroundColor = 0xFF121212)
@Composable
private fun ItemSliderDarkPreview() {
    Theme(darkTheme = true) {
        LineChart(listOf(1f, 3f, 4f, 8f, 10f, 11f, 10f, 11f, 9f, 8f, 11f))
    }
}

@Composable
fun LineChart(
    dataPoints: List<Float>,
    modifier: Modifier = Modifier
) {
    val lineColor = MaterialTheme.colorScheme.primary
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f),
        onDraw = {
            if (dataPoints.firstOrNull() != null) {
                val max = dataPoints.max()
                val min = dataPoints.min()
                val widthFraction = size.width / (dataPoints.size - 1)
                for (i in 0 until dataPoints.size - 1) {
                    val fromValuePercentage = getValuePercentageForRange(dataPoints[i], max, min)
                    val toValuePercentage = getValuePercentageForRange(dataPoints[i + 1], max, min)
                    val fromPoint = Offset(
                        x = i * widthFraction,
                        y = size.height.times(1 - fromValuePercentage)
                    )
                    val toPoint =
                        Offset(
                            x = (i + 1) * widthFraction,
                            y = size.height.times(1 - toValuePercentage)
                        )
                    drawLine(
                        color = lineColor,
                        start = fromPoint,
                        end = toPoint,
                        strokeWidth = 3f
                    )
                }
            }
        }

    )
}


private fun getValuePercentageForRange(value: Float, max: Float, min: Float): Float {
    val range = max - min
    return if (range == 0f) 0.5f else (value - min) / range
}