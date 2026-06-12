package org.opengripboard.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp

import org.opengripboard.model.OgbViewModel
import org.opengripboard.model.PageId
import org.opengripboard.theming.Theme
import org.opengripboard.ui.preview.ModelProvider
import org.opengripboard.ui.views.ConnectBoard
import org.opengripboard.ui.views.Dashboard
import org.opengripboard.ui.views.PastTrainings
import org.opengripboard.ui.views.RecordingData


@OptIn(ExperimentalComposeUiApi::class)
@Composable
@Preview
fun App(@PreviewParameter(ModelProvider::class) model: OgbViewModel) {
    Theme {
        Scaffold { padding ->
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .safeContentPadding()
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.height(50.dp).padding(padding))
                ViewContent(model)
            }
        }
    }
}

@Composable
fun ViewContent(model: OgbViewModel) {
    when (model.navigation.currentPage) {
        PageId.Dashboard -> {
            Dashboard(model)
        }

        PageId.ConnectBoard -> {
            ConnectBoard(
                { model.navigation.navigateBack() },
                model.hasCameraPermission,
                model.flashIsEnabled,
                { result -> model.onQrScannerResult(result) },
                { model.onFlashButtonPressed() },
                { model.onOpenSettings() })
        }

        PageId.PastTrainings -> {
            PastTrainings(
                model.trainings.pastTrainings,
                { model.onNewRecordingPressed() },
                { id -> model.trainings.onDeletePressed(id) },
                { model.navigation.navigateBack() },
            )
        }

        PageId.RecordingData -> {
            RecordingData(
                { model.navigation.navigateBack() },
                model.hangboards.isRecording,
                model.hangboards.currentReadings,
                { model.hangboards.onStartRecording() },
                { model.onHangboardRecordingStopped() },
                { model.onAddHangboard() },
                { id -> model.onHangboardSelected(id) },
                model.hangboards.availableHangboards,
                model.hangboards.currentHangboard,
            )
        }
    }
}