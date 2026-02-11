package org.opengripboard.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import org.opengripboard.data.objects.Hangboard

class HangboardsManager {
    var isRecording by mutableStateOf(false)
        private set
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
        isRecording = true
    }

    fun onStopRecording() {
        isRecording = false
    }

    fun addHangboard(newHangboard: Hangboard) {
        availableHangboards.add(newHangboard)
    }

    fun addHangboards(newHangboards: List<Hangboard>) {
        newHangboards.forEach { hangboard -> availableHangboards.add(hangboard) }
    }
}