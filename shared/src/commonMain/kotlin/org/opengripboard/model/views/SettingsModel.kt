package org.opengripboard.model.views

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import org.opengripboard.data.SettingsRepository
import org.opengripboard.model.TrainingsManager
import org.opengripboard.ui.customAppLocale

class SettingsModel(
    private val settingsRepository: SettingsRepository,
    private val trainingsManager: TrainingsManager,
) {
    var showDeleteModal by mutableStateOf(false)
        private set

    fun onLanguageSelected(locale: String) {
        customAppLocale = locale
        settingsRepository.language = locale
    }

    fun onDeleteAllTrainings(){
        showDeleteModal = true
    }

    fun onDismissModal () {
        showDeleteModal = false
    }

    fun onDeleteAllTrainigsConfirmed(){
        trainingsManager.onDeleteAll()
        showDeleteModal = false
    }
}