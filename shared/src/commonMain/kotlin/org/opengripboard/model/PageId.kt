package org.opengripboard.model

import opengripboard.shared.generated.resources.Res
import opengripboard.shared.generated.resources.connect_hangboard
import opengripboard.shared.generated.resources.dashboard
import opengripboard.shared.generated.resources.past_trainings
import opengripboard.shared.generated.resources.recording_data
import opengripboard.shared.generated.resources.settings
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

enum class PageId(val display: StringResource, val requiresCameraPermissions: Boolean) {
    Dashboard(Res.string.dashboard, false),
    ConnectBoard(Res.string.connect_hangboard, true),
    PastTrainings(Res.string.past_trainings, false),
    RecordingData(Res.string.recording_data, false),
    Settings(Res.string.settings, false)
}