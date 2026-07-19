package org.opengripboard.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class NavigationManager(private val onPageEntered: (PageId) -> Unit) {
    var currentPage by mutableStateOf(PageId.Dashboard)
        private set

    private val history = mutableListOf<PageId>()

    fun navigate(to: PageId) {
        if (to != currentPage) {
            history.add(currentPage)
            currentPage = to
            onPageEntered(to)
        }
    }

    fun navigateBack() {
        if (history.isNotEmpty()) {
            currentPage = history.removeLast()
        }
    }

    fun onBarChartClick() {
        if (currentPage == PageId.Dashboard) {
            navigate(PageId.PastTrainings)
        }
    }

    fun onHangboardSelected() {
        if (currentPage == PageId.Dashboard) {
            navigate(PageId.RecordingData)
        }
    }

    fun onAddHangboardSelected(){
        if (currentPage == PageId.Dashboard) {
            navigate(PageId.ConnectBoard)
        }
    }

    fun onSettingsSelected(){
        if(currentPage == PageId.Dashboard){
            navigate(PageId.Settings)
        }
    }
}
