package org.opengripboard.data

import com.russhwolf.settings.Settings

class DefaultSettingsRepository(
    private val settings: Settings = Settings()
) : SettingsRepository {

    override var language: String
        get() = settings.getString("language", "en")
        set(value) {
            settings.putString("language", value)
        }
}