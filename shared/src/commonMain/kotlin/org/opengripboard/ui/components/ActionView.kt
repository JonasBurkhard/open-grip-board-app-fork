package org.opengripboard.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.opengripboard.theming.Theme

@Preview(name = "Light", showBackground = true)
@Composable
private fun ItemSliderLightPreview() {
    Theme(darkTheme = false) {
        ActionView("Preview", {}) {Text("preview content")}
    }
}

@Preview(name = "Dark", showBackground = true, backgroundColor = 0xFF121212)
@Composable
private fun ItemSliderDarkPreview() {
    Theme(darkTheme = true) {
        ActionView("Preview", {}) {Text("preview content")}
    }
}

@Composable
fun ActionView(
    title: String,
    onBackPressed: () -> Unit,
    content: @Composable () -> Unit = { Text("Content") }
) {
    Column (modifier = Modifier.fillMaxSize()){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            IconButton(Icons.AutoMirrored.Filled.ArrowBack, onBackPressed)
            Text(
                text = title,
                modifier = Modifier.align(Alignment.Center),
                color = MaterialTheme.colorScheme.onBackground
            )
        }
        Box(modifier = Modifier.weight(1f)) {
            content()
        }
    }
}

