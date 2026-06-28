package org.opengripboard.ui.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import opengripboard.shared.generated.resources.Res
import opengripboard.shared.generated.resources.language
import org.jetbrains.compose.resources.stringResource
import org.opengripboard.model.OgbViewModel
import org.opengripboard.theming.Theme
import org.opengripboard.theming.spacing
import org.opengripboard.ui.components.ActionView
import org.opengripboard.ui.components.SectionHeaderText
import org.opengripboard.ui.preview.ModelProvider

@Preview(name = "Light", showBackground = true)
@Composable
private fun ItemSliderLightPreview(@PreviewParameter(ModelProvider::class) model: OgbViewModel) {
    Theme(darkTheme = false) {
        Settings(model)
    }
}

@Preview(name = "Dark", showBackground = true, backgroundColor = 0xFF121212)
@Composable
private fun ItemSliderDarkPreview(@PreviewParameter(ModelProvider::class) model: OgbViewModel) {
    Theme(darkTheme = true) {
        Settings(model)
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun Settings(model: OgbViewModel) {
    var dropDownIsVisible by remember { mutableStateOf(false) }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
        modifier = Modifier.fillMaxWidth()
    ) {
        ActionView(model.navigation.currentPage.name, { model.navigation.navigateBack() }) {
            Column {
                SectionHeaderText(stringResource(Res.string.language))
                Box (modifier = Modifier.padding(MaterialTheme.spacing.medium)){
                    Button(onClick = { dropDownIsVisible = !dropDownIsVisible }) {
                        Text("select language")
                    }
                }
                DropdownMenu(
                    expanded = dropDownIsVisible,
                    onDismissRequest = { dropDownIsVisible = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("English") },
                        onClick = { model.onLanguageSelected("en") }
                    )
                    DropdownMenuItem(
                        text = { Text("Deutsch") },
                        onClick = { model.onLanguageSelected("de") }
                    )
                }
            }
        }
    }
}
