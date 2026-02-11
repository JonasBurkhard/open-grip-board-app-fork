package org.opengripboard.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import org.opengripboard.data.objects.Hangboard
import kotlin.time.Clock
import kotlin.time.Duration
import kotlin.time.Instant

class HangboardsManager {
    var isRecording by mutableStateOf(false)
        private set
    private var recordingStartTime: LocalDateTime? = null
    var currentReadings = mutableStateListOf<Int>()
        private set
    var availableHangboards = mutableStateListOf<Hangboard>()
        private set
    var currentHangboard by mutableStateOf<Hangboard?>(null)
        private set

    fun onSelected(hangboardId: Int) {
        currentHangboard =
            availableHangboards.firstOrNull { hangboard -> hangboard.hangboardId == hangboardId }
    }

    fun onStartRecording() {
        currentReadings.clear()
        recordingStartTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        isRecording = true
    }

    fun onStopRecording(): Duration {
        isRecording = false
        recordingStartTime?.let { startTime ->
            return Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
                .toInstant(TimeZone.currentSystemDefault()) -
                    startTime.toInstant(TimeZone.currentSystemDefault())
        }
        return Duration.ZERO
    }

    fun addHangboard(newHangboard: Hangboard) {
        availableHangboards.add(newHangboard)
    }

    fun addHangboards(newHangboards: List<Hangboard>) {
        newHangboards.forEach { hangboard -> availableHangboards.add(hangboard) }
    }

    fun onNewMqttMessage(message: String) {
        if (isRecording) {
            val reading = message.toIntOrNull()
            if (reading != null) {
                currentReadings.add(reading)
            }
        }
    }

}