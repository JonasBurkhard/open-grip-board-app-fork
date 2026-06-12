package org.opengripboard.model

enum class PageId(val display: String, val requiresCameraPermissions: Boolean) {
    Dashboard("Dashboard",false),
    ConnectBoard("Connect a new Hangboard", true),
    PastTrainings("Your past trainings",false),
    RecordingData("Recording data",false),
}