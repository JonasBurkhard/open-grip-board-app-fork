package org.opengripboard.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidedValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import org.opengripboard.model.getDefaultLocale

var customAppLocale by mutableStateOf<String?>(getDefaultLocale())

expect object LocalAppLocale {
    val current: String
        @Composable get

    @Composable
    infix fun provides(value: String?): ProvidedValue<*>
}

@Composable
fun AppEnvironment(
    content: @Composable () -> Unit,
) {
    androidx.compose.runtime.CompositionLocalProvider(
        LocalAppLocale provides customAppLocale,
    ) {
        androidx.compose.runtime.key(customAppLocale) {
            content()
        }
    }
}