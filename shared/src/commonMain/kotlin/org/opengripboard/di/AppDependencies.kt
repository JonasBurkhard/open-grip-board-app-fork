package org.opengripboard.di

import com.russhwolf.settings.Settings
import org.opengripboard.data.DefaultSettingsRepository

object AppDependencies {
    val settingsRepository = DefaultSettingsRepository(
        Settings()
    )
}