package org.opengripboard.model.views

import org.opengripboard.data.SettingsRepository
import org.opengripboard.ui.customAppLocale

class SettingsModel(
    private val settingsRepository: SettingsRepository,
) {
    fun onLanguageSelected(locale: String) {
        customAppLocale = locale
        settingsRepository.language = locale
    }
}