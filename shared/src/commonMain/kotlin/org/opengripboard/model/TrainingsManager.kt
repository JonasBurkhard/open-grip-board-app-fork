package org.opengripboard.model

import androidx.compose.runtime.mutableStateListOf
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.opengripboard.data.LocalStorageService
import org.opengripboard.data.objects.Training
import kotlin.time.Clock
import kotlin.time.Duration
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class TrainingsManager(private val localStorageService: LocalStorageService) {
    var pastTrainings = mutableStateListOf<Training>()
        private set

    fun deleteTraining(trainingToDelete: Training) {
        pastTrainings.removeAll { training -> training.id == trainingToDelete.id }
        localStorageService.deleteTraining(trainingToDelete.id)
    }

    fun onDeleteAll() {
        pastTrainings.toList().forEach { training ->
            deleteTraining(training)
        }
    }

    fun addTraining(newTraining: Training) {
        pastTrainings.add(newTraining)
        localStorageService.saveTraining(newTraining)
    }

    fun addTrainings(newTrainings: List<Training>) {
        newTrainings.forEach { training -> pastTrainings.add(training) }
    }

    @OptIn(ExperimentalUuidApi::class)
    fun addTrainingFromReadings(readings: List<Int>, duration: Duration) {
        if (readings.isEmpty()) {
            return
        }
        addTraining(
            Training(
                id = Uuid.random().toString(),
                date = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
                dataPoints = readings,
                duration = duration,
            )
        )
    }

}