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
import org.opengripboard.data.SettingsRepository
import org.opengripboard.data.objects.Hangboard
import org.opengripboard.data.objects.HangboardStatus
import org.opengripboard.model.views.DashboardModel
import org.opengripboard.model.views.SettingsModel
import kotlin.time.Duration

class OgbViewModel(
    private val localStorageService: LocalStorageService,
    private val settingsRepository: SettingsRepository,
    private val mqttService: MqttService,
    val statistics: StatisticsManager = StatisticsManager(),
    val trainings: TrainingsManager = TrainingsManager(),
    val hangboards: HangboardsManager = HangboardsManager(),
) : ViewModel() {
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

    init {
        var loadedHangboards = localStorageService.loadAllHangboards()
        hangboards.addHangboards(loadedHangboards)
        var loadedTrainings = localStorageService.loadAllTrainings()
        trainings.addTrainings(loadedTrainings)
    }

    fun onNewRecordingPressed() {
        navigation.navigate(PageId.RecordingData)
    }

    fun onPageEntered(pageId: PageId) {
        if (pageId.requiresCameraPermissions && !hasCameraPermission) {
            requestCameraPermission()
        }
    }

    fun onOpenSettings() {
        openSettingsEvent = true
    }

    fun onOpenSettingsHandled() {
        openSettingsEvent = false
        navigation.navigateBack()
    }

    /// Hangboard ///
    fun onAddHangboard() {
        navigation.onAddHangboardSelected()
    }

    fun onHangboardSelected(hangboardListIndex: Int) {
        val selectedBoard = hangboards.availableHangboards[hangboardListIndex]
        hangboards.onSelected(selectedBoard.hangboardId)
        subscribeToHangboard(selectedBoard.hangboardId)
        navigation.onHangboardSelected()
    }

    fun onHangboardRecordingStopped() {
        val duration: Duration = hangboards.onStopRecording()
        trainings.addTrainingFromReadings(hangboards.currentReadings, duration)
    }

    /// QR Camera ///
    fun onQrScannerResult(scan: String) {
        if (scan != "notFound") {
            hangboards.addHangboard(
                Hangboard(scan, scan, HangboardStatus.Offline)
            )
            val allHangboards = hangboards.availableHangboards
            allHangboards.forEach { hangboard -> localStorageService.saveHangboard(hangboard) }
            navigation.navigate(PageId.RecordingData)
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

    fun subscribeToHangboard(hangboardId: String) {
        val topic = "${hangboardId}/#"
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

    val settingsModel = SettingsModel(settingsRepository, trainings)
    val dashboardModel = DashboardModel(navigation)

}