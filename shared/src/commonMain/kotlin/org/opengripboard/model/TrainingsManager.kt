package org.opengripboard.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import org.opengripboard.data.LocalStorageService
import opengripboard.shared.generated.resources.Res
import opengripboard.shared.generated.resources.error_no_hangboard_selected
import org.jetbrains.compose.resources.StringResource
import org.opengripboard.data.objects.GripType
import org.opengripboard.data.objects.Side
import org.opengripboard.data.objects.Training
import kotlin.time.Clock
import kotlin.time.Duration
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class TrainingsManager(
    private val localStorageService: LocalStorageService,
    val hangboardsManager: HangboardsManager,
    val postErrorMsg: (StringResource) -> Unit
) {
    var pastTrainings = mutableStateListOf<Training>()
        private set

    var availableGripTypes = mutableStateListOf<GripType>(
        GripType("full Hand", Side.Right),
        GripType("full Hand", Side.Left),
    )
        private set

    var currentGripType by mutableStateOf<GripType>(availableGripTypes.first())
        private set
    private var recordingStartTime: LocalDateTime? = null

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

    fun onStartRecordingTraining() {
        if (hangboardsManager.currentHangboard == null) {
            postErrorMsg(Res.string.error_no_hangboard_selected)
        } else {
            hangboardsManager.startRecording()
            recordingStartTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        }
    }

    fun onStopRecordingTraining() {
        hangboardsManager.onStopRecording()
        val duration = recordingStartTime?.let { startTime ->
            Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
                .toInstant(TimeZone.currentSystemDefault()) - startTime.toInstant(TimeZone.currentSystemDefault())
        } ?: Duration.ZERO
        addTrainingFromReadings(hangboardsManager.currentReadings, duration, currentGripType)
    }

    fun onGripTypeSelected(gripType: GripType) {
        currentGripType = gripType
    }

    @OptIn(ExperimentalUuidApi::class)
    fun addTrainingFromReadings(readings: List<Int>, duration: Duration, gripType: GripType) {
        if (readings.isEmpty()) {
            return
        }
        addTraining(
            Training(
                id = Uuid.random().toString(),
                date = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
                dataPoints = readings,
                duration = duration,
                gripType = gripType,
            )
        )
    }
}