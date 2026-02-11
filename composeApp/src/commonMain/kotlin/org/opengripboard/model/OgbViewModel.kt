package org.opengripboard.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.opengripboard.data.MqttService
import org.opengripboard.data.objects.Hangboard
import org.opengripboard.data.objects.HangboardStatus
import kotlin.time.Duration

class OgbViewModel(
    val statistics: StatisticsManager = StatisticsManager(),
    val navigation: NavigationManager = NavigationManager(),
    val trainings: TrainingsManager = TrainingsManager(),
    val hangboards: HangboardsManager = HangboardsManager(),
) : ViewModel() {
    lateinit var mqttService: MqttService

    var currentError by mutableStateOf<String?>(null)
    var hasCameraPermission by mutableStateOf(false)
        private set

    var flashIsEnabled by mutableStateOf(false)
        private set

    fun onNewRecordingPressed() {
        navigation.navigate(PageId.RecordingData)
    }

    /// Hangboard ///
    fun onAddHangboard() {
        navigation.navigate(PageId.ConnectBoard)
    }
    fun onHangboardSelected(hangboardId: Int) {
        hangboards.onSelected(hangboardId)
        subscribeToHangboard(hangboardId)
        navigation.onHangboardSelected()
    }

    fun onHangboardRecordingStopped(){
        val duration: Duration = hangboards.onStopRecording()
        trainings.addTrainingFromReadings(hangboards.currentReadings, duration)
    }

    /// QR Camera ///
    fun onQrScannerResult(result: String) {
        val scan = result.toIntOrNull()
        scan?.let {
            hangboards.addHangboard(
                Hangboard(scan.toString(), scan, HangboardStatus.Offline)
            )
        }
    }

    fun onFlashButtonPressed() {
        flashIsEnabled = !flashIsEnabled
    }

    /// Error Snack bar //
    fun clearError() {
        currentError = null
    }

    /// MQTT ///
    fun subscribeToCurrentHangboard() {
        hangboards.currentHangboard?.hangboardId?.let {
            subscribeToHangboard(it)
        }
    }

    fun subscribeToHangboard(hangboardId: Int) {
        val topic = "hangboards/${hangboardId}"
        subscribeToMqttTopic(
            topic,
            { msg -> hangboards.onNewMqttMessage(msg) },
            ::onHangboardSubscriptionFail
        )
    }

    fun onHangboardSubscriptionFail() {
        currentError = "failed to subscribe to hangboard"
    }

    fun subscribeToMqttTopic(
        topic: String,
        onNewMqttMessage: (String) -> Unit,
        onMqttSubscribeFail: () -> Unit
    ) {
        viewModelScope.launch(Dispatchers.Default) {
            mqttService.connectAndSubscribe(
                topic, { message ->
                    viewModelScope.launch(Dispatchers.Main) {
                        onNewMqttMessage(message)
                    }
                },
                {
                    viewModelScope.launch(Dispatchers.Main) {
                        onMqttSubscribeFail()
                    }
                })
        }
    }
}