package org.opengripboard.model

import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

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
}