package org.opengripboard.data.objects

import kotlinx.serialization.Serializable

@Serializable
data class Hangboard(
    val name: String,
    val hangboardId: String,
    var status: HangboardStatus
)