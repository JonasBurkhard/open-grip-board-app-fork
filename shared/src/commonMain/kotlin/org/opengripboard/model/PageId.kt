package org.opengripboard.model

import opengripboard.shared.generated.resources.Res
import opengripboard.shared.generated.resources.settings

enum class PageId(val display: String, val requiresCameraPermissions: Boolean) {
    Dashboard("Dashboard",false),
    ConnectBoard("Connect a new Hangboard", true),
    PastTrainings("Your past trainings",false),
    RecordingData("Recording data",false),
    Settings(Res.string.settings.toString(), false)
}