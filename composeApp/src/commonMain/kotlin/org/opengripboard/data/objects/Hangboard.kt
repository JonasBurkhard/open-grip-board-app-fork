package org.opengripboard.data.objects

data class Hangboard(
    val name: String,
    val hangboardId: Int,
    var status: HangboardStatus
)