package org.opengripboard.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.opengripboard.data.LocalStorageService
import org.opengripboard.data.MqttService
import org.opengripboard.data.objects.Hangboard
import org.opengripboard.data.objects.HangboardStatus
import kotlin.time.Duration

class OgbViewModel(
    val localStorageService: LocalStorageService,
    val statistics: StatisticsManager = StatisticsManager(),
    val trainings: TrainingsManager = TrainingsManager(),
    val hangboards: HangboardsManager = HangboardsManager(),
) : ViewModel() {
    lateinit var mqttService: MqttService
    val navigation = NavigationManager(::onPageEntered)
    var currentError by mutableStateOf<String?>(null)
    var hasCameraPermission by mutableStateOf(false)
        private set
    var shouldRequestCameraPermission by mutableStateOf(false)
        private set
    var openSettingsEvent by mutableStateOf(false)
        private set

    var flashIsEnabled by mutableStateOf(false)
        private set

    fun onNewRecordingPressed() {
        navigation.navigate(PageId.RecordingData)
    }
    fun onPageEntered(pageId: PageId){
        if (pageId.requiresCameraPermissions && !hasCameraPermission){
            requestCameraPermission()
        }
    }

    fun onOpenSettings(){
        openSettingsEvent = true
    }

    fun onOpenSettingsHandled(){
        openSettingsEvent = false
        navigation.navigateBack()
    }

    /// Hangboard ///
    fun onAddHangboard() {
        println(hasCameraPermission)
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
        println(scan)
        scan?.let {
            hangboards.addHangboard(
                Hangboard(scan.toString(), scan, HangboardStatus.Offline)
            )
        }
    }

    fun onCameraPermissionResult(granted: Boolean) {
        hasCameraPermission = granted
        shouldRequestCameraPermission = false
    }

    fun requestCameraPermission() {
        shouldRequestCameraPermission = true
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