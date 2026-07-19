package org.opengripboard.model

import org.opengripboard.data.objects.Training
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.Duration

class StatisticsManager {
    val trainingDurationLastTwoWeeks = mutableStateListOf<Pair<LocalDate, Duration>>()

    var trainingDurationTwoWeekAverage by mutableStateOf(Duration.ZERO)
        private set

    var trainingDurationToday by mutableStateOf(Duration.ZERO)
        private set

    fun recalculateFor(page: PageId, pastTrainings: List<Training>) {
        if (page != PageId.Dashboard) return
        val today: LocalDate =
            Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
        val startDate: LocalDate = today.minus(DatePeriod(days = 14))
        val recentTrainings = pastTrainings.filter { it.date.date in startDate..today }

        val dayDurations = (0..14).associate { offset ->
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
        trainingDurationLastTwoWeeks.clear()
        trainingDurationLastTwoWeeks.addAll(orderedDurations)

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