package org.opengripboard.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.window.Dialog
import opengripboard.shared.generated.resources.Res
import opengripboard.shared.generated.resources.are_you_sure
import opengripboard.shared.generated.resources.delete_all_recorded_training_data
import opengripboard.shared.generated.resources.delete_data
import opengripboard.shared.generated.resources.keep_data
import opengripboard.shared.generated.resources.language
import opengripboard.shared.generated.resources.personal_training_data
import org.jetbrains.compose.resources.stringResource
import org.opengripboard.model.OgbViewModel
import org.opengripboard.model.views.SettingsModel
import org.opengripboard.theming.Theme
import org.opengripboard.theming.spacing
import org.opengripboard.ui.components.ActionView
import org.opengripboard.ui.components.SectionHeaderText
import org.opengripboard.ui.customAppLocale
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

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun Settings(model: OgbViewModel) {
    var expanded by remember { mutableStateOf(false) }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
        modifier = Modifier.fillMaxWidth()
    ) {
        ActionView(stringResource(model.navigation.currentPage.display), { model.navigation.navigateBack() }) {
            Column {
                SectionHeaderText(stringResource(Res.string.language))
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded },
                ) {
                    TextField(
                        value = customAppLocale?:"undefined",
                        onValueChange = {},
                        readOnly = true,
                        label = { "Language" },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(
                                expanded = expanded
                            )
                        },
                        modifier = Modifier
                            .menuAnchor()
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("English") },
                            onClick = {
                                model.settingsModel.onLanguageSelected("en")
                                expanded = false
                            }
                        )

                        DropdownMenuItem(
                            text = { Text("Deutsch") },
                            onClick = {
                                model.settingsModel.onLanguageSelected("de")
                                expanded = false
                            }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))
                Text(stringResource(Res.string.personal_training_data))
                Button(
                    onClick = { model.settingsModel.onDeleteAllTrainings() }
                ) {
                    Text(stringResource(Res.string.delete_all_recorded_training_data))
                }
                if (model.settingsModel.showDeleteModal) {
                    DeleteTrainingDialog(model.settingsModel)
                }
            }
        }
    }
}

@Composable
fun DeleteTrainingDialog(model: SettingsModel) {
    Dialog(
        onDismissRequest = { model.onDismissModal() }
    ) {
        Box(
            modifier = Modifier
                .background(
                    MaterialTheme.colorScheme.surface,
                    MaterialTheme.shapes.large
                )
                .padding(MaterialTheme.spacing.large)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(stringResource(Res.string.are_you_sure))
                Spacer(Modifier.height(MaterialTheme.spacing.large))
                Row {
                    Button(
                        onClick = { model.onDismissModal() }
                    ) {
                        Text(stringResource(Res.string.keep_data))
                    }
                    Spacer(Modifier.width(MaterialTheme.spacing.large))
                    Button(
                        onClick = { model.onDeleteAllTrainigsConfirmed() },
                        colors = ButtonColors(containerColor = MaterialTheme.colorScheme.error, contentColor = MaterialTheme.colorScheme.onPrimary, disabledContainerColor = Color.Transparent, disabledContentColor = Color.Transparent)
                    ) {
                        Text(stringResource(Res.string.delete_data))
                    }
                }
            }
        }
    }
}
