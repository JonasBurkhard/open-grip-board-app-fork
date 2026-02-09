package org.opengripboard.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class NavigationManager {
    var currentPage by mutableStateOf(PageId.Dashboard)
        private set

    private val history = mutableListOf<PageId>()

    fun navigate(to: PageId) {
        if (to != currentPage) {
            history.add(currentPage)
            currentPage = to
        }
    }

    fun navigateBack(): Boolean {
        return if (history.isNotEmpty()) {
            currentPage = history.removeLast()
            true
        } else {
            false
        }
    }

    fun canGoBack(): Boolean = history.isNotEmpty()
}
