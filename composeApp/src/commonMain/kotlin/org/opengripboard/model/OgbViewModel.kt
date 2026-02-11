package org.opengripboard.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.opengripboard.data.objects.Hangboard
import org.opengripboard.data.objects.HangboardStatus

class OgbViewModel(
    val statistics: StatisticsManager = StatisticsManager(),
    val navigation: NavigationManager = NavigationManager(),
    val trainings: TrainingsManager = TrainingsManager(),
    val hangboards: HangboardsManager = HangboardsManager(),
) : ViewModel() {

    init {
        viewModelScope.launch {
            snapshotFlow { navigation.currentPage }.collect { page ->
                statistics.recalculateFor(page, trainings.pastTrainings)
            }
        }
    }

    var hasCameraPermission by mutableStateOf(false)
        private set

    var flashIsEnabled by mutableStateOf(false)
        private set

    fun onNewRecordingPressed() {
        navigation.navigate(PageId.RecordingData)
    }

    fun onAddHangboard() {
        navigation.navigate(PageId.ConnectBoard)
    }

    fun onSelectHangboard(hangboardId: Int) {
        hangboards.onSelected(hangboardId)
        navigation.onHangboardSelected()
    }

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
}