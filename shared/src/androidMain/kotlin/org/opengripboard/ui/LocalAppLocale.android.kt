package org.opengripboard.ui

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidedValue
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import java.util.Locale
import androidx.compose.ui.platform.LocalLocale
import androidx.compose.ui.platform.LocalResources

actual object LocalAppLocale {

    private var default: Locale? = null

    actual val current: String
        @Composable
        get() = LocalLocale.current.platformLocale.toString()

    @Composable
    actual infix fun provides(value: String?): ProvidedValue<*> {
        val configuration = LocalConfiguration.current

        if (default == null) {
            default = LocalLocale.current.platformLocale
        }

        val locale = value?.let(::Locale) ?: default!!

        Locale.setDefault(locale)

        configuration.setLocale(locale)

        val resources = LocalResources.current
        resources.updateConfiguration(configuration, resources.displayMetrics)

        return LocalConfiguration.provides(configuration)
    }
}