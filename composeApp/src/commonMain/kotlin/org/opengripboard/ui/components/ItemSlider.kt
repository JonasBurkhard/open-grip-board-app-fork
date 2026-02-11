package org.opengripboard.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.opengripboard.model.OgbViewModel
import org.opengripboard.theming.MediumSpacing
import org.opengripboard.theming.Theme
import org.opengripboard.ui.preview.ModelProvider
import org.opengripboard.utils.getScreenWidth

@Preview(name = "Light", showBackground = true)
@Composable
private fun ItemSliderLightPreview(@PreviewParameter(ModelProvider::class) model: OgbViewModel) {
    Theme(darkTheme = false) {
        ItemSlider({}, {}) {
            listOf(
                { Text("Content", color = MaterialTheme.colorScheme.onPrimaryContainer) },
                { Text("Content", color = MaterialTheme.colorScheme.onPrimaryContainer) }
            )
        }
    }
}

@Preview(name = "Dark", showBackground = true, backgroundColor = 0xFF121212)
@Composable
private fun ItemSliderDarkPreview(@PreviewParameter(ModelProvider::class) model: OgbViewModel) {
    Theme(darkTheme = true) {
        ItemSlider({}, {}) {
            listOf(
                { Text("Content", color = MaterialTheme.colorScheme.onPrimaryContainer) },
                { Text("Content", color = MaterialTheme.colorScheme.onPrimaryContainer) }
            )
        }
    }
}

@Composable
fun ItemSlider(
    onAddPressed: () -> Unit,
    onItemSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
    itemsList: () -> List<@Composable () -> Unit>,
) {
    val screenWidth = getScreenWidth() / 2.7
    val boxWidth: Dp = (screenWidth / 2.75).dp

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        itemsIndexed(itemsList()) { index, item ->
            ItemsBox(boxWidth, { onItemSelected(index) }) { item() }
        }
        item {
            ItemsBox(boxWidth, onAddPressed) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Item",
                    tint = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Composable
fun ItemsBox(boxWidth: Dp, onItemSelected: () -> Unit, item: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .width(boxWidth)
            .height(boxWidth * 0.8f)
            .border(
                1.dp,
                MaterialTheme.colorScheme.tertiary,
                MaterialTheme.shapes.medium
            )
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.primaryContainer)
            .clickable { onItemSelected() }
            .padding(MediumSpacing)
    ) {
        item()
    }
}