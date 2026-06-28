package org.opengripboard.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import org.opengripboard.theming.spacing

@Composable
fun SectionHeaderText(headerText: String) {
    Text(
        headerText,
        modifier = Modifier
            .padding(MaterialTheme.spacing.medium)
            .fillMaxWidth(),
        textAlign = TextAlign.Start,
        color = MaterialTheme.colorScheme.onBackground
    )
}