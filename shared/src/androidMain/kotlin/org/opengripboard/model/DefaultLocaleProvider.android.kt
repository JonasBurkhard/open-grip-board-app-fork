package org.opengripboard.model

import java.util.Locale

actual fun getDefaultLocale(): String {
    return Locale.getDefault().toString()
}