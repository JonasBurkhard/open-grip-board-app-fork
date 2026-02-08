package org.opengripboard.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import org.opengripboard.data.objects.Training
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.Duration.Companion.days

class StatisticsManager {
    var trainingDurationLastTwoWeeks = mutableStateListOf<Pair<LocalDate, Duration>>()
        private set

    var trainingDurationTwoWeekAverage by mutableStateOf(Duration.ZERO)
        private set

    var trainingDurationToday by mutableStateOf(Duration.ZERO)
        private set

    fun recalculateFor(page: PageId, pastTrainings: List<Training>) {
        if (page != PageId.Dashboard) return
        val timeZone = TimeZone.currentSystemDefault()
        val today: LocalDate = Clock.System.now().toLocalDateTime(timeZone).date
        val startDate: LocalDate = today.minus(DatePeriod(days = 14))
        val recentTrainings = pastTrainings.filter { it.date.date in startDate..today }

        val dayDurations = (0..13).associate { offset ->
            val date: LocalDate = startDate.plus(DatePeriod(days = offset))
            date to Duration.ZERO
        }.toMutableMap()

        recentTrainings
            .forEach { training ->
                val trainingDate = training.date.date
                dayDurations[trainingDate] = dayDurations.getValue(trainingDate) + training.duration
            }
        val orderedDurations = dayDurations.entries
            .sortedBy { it.key }
            .map { it.key to it.value }
        trainingDurationLastTwoWeeks = mutableStateListOf(*orderedDurations.toTypedArray())

        trainingDurationTwoWeekAverage =
            if (orderedDurations.isNotEmpty()) {
                orderedDurations
                    .map { it.second }
                    .reduce { acc, d -> acc + d } / orderedDurations.size
            } else Duration.ZERO

        trainingDurationToday =
            dayDurations[today] ?: Duration.ZERO
    }
}