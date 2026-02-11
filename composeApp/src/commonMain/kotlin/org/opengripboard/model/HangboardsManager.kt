package org.opengripboard.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import org.opengripboard.data.objects.Hangboard

class HangboardsManager {
    var isRecording by mutableStateOf(false)
    var currentReadings = mutableStateListOf<Int>()
    var availableHangboards = mutableStateListOf<Hangboard>()
    var currentHangboard by mutableStateOf<Hangboard?>(null)

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
}