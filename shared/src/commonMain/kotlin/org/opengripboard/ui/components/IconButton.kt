package org.opengripboard.ui.components

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import org.opengripboard.theming.Theme

@Composable
@Preview(
    name = "Light",
    showBackground = true
)
@Preview(
    name = "Dark",
    showBackground = true,
    backgroundColor = 0xFF121212,
    uiMode = 1
)
private fun ItemSliderPreview() {
    Theme {
        IconButton(Icons.AutoMirrored.Filled.ArrowBack, {})
    }
}

@Composable
fun IconButton(icon: ImageVector, onButtonPressed: () -> Unit, modifier: Modifier = Modifier) {
    IconButton(
        onClick = onButtonPressed,
        modifier = modifier
    ) {
        Icon(
            icon,
            contentDescription = "go back",
            modifier = modifier.fillMaxHeight(),
            tint = MaterialTheme.colorScheme.onBackground
        )
    }
}