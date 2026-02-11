package org.opengripboard.data.objects

import kotlinx.datetime.LocalDateTime
import kotlin.time.Duration

data class Training(
    val id: String,
    val date: LocalDateTime,
    val dataPoints: List<Int>,
    val duration: Duration,
)