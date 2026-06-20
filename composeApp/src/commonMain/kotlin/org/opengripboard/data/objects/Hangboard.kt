package org.opengripboard.data.objects

data class Hangboard(
    val name: String,
    val hangboardId: String,
    var status: HangboardStatus
)