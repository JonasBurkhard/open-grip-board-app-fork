package org.opengripboard.model

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.opengripboard.data.objects.Training

class OgbViewModel(
    val statistics: StatisticsManager = StatisticsManager(),
    val navigation: NavigationManager = NavigationManager()
) : ViewModel() {
    val pastTrainings = mutableStateListOf<Training>()

    init {
        viewModelScope.launch {
            snapshotFlow { navigation.currentPage }.collect { page ->
                statistics.recalculateFor(page, pastTrainings)
            }
        }
    }
}