package org.opengripboard.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
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

    // Remember the path and points to avoid recalculating on every recomposition
    val points = remember(dataPoints) {
        if (dataPoints.size <= 1) emptyList()
        else {
            val max = dataPoints.max() + 1f
            val min = dataPoints.min() - 1f
            dataPoints.mapIndexed { i, value ->
                Offset(
                    x = i.toFloat(),
                    y = 1f - getValuePercentageForRange(value, max, min)
                )
            }
        }
    }

    val path = remember(points) {
        Path().apply {
            if (points.isNotEmpty()) {
                moveTo(points.first().x, points.first().y)
                for (i in 0 until points.size - 1) {
                    val current = points[i]
                    val next = points[i + 1]
                    val controlX = (current.x + next.x) / 2
                    cubicTo(
                        controlX, current.y,
                        controlX, next.y,
                        next.x, next.y
                    )
                }
            }
        }
    }

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f),
        onDraw = {
            if (points.isNotEmpty()) {
                // Scale points to canvas size
                val widthFraction = size.width / (points.size - 1)
                val scaledPoints = points.map { point ->
                    Offset(
                        x = point.x * widthFraction,
                        y = point.y * size.height
                    )
                }

                drawPath(
                    path = path,
                    color = lineColor,
                    style = Stroke(width = 6f)
                )

                drawCircle(
                    color = lineColor,
                    radius = 20f,
                    center = scaledPoints.last()
                )
            }
        }
    )
}

private fun getValuePercentageForRange(value: Float, max: Float, min: Float): Float {
    val range = max - min
    return if (range == 0f) 0.5f else (value - min) / range
}