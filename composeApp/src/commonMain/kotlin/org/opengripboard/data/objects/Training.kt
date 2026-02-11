package org.opengripboard.data.objects

import kotlinx.datetime.LocalDateTime
import kotlin.time.Duration

class Training(
    val id: Int,
    val date: LocalDateTime,
    val dataPoints: List<Int>,
    val duration: Duration,
) {

}