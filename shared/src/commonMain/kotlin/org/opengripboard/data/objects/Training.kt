package org.opengripboard.data.objects

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import kotlin.time.Duration

@Serializable
data class Training(
    val id: String,
    val date: LocalDateTime,
    val dataPoints: List<Int>,
    val duration: Duration,
)
