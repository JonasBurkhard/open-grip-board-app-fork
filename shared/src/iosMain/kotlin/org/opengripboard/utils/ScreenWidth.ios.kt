package org.opengripboard.utils

import kotlinx.cinterop.ExperimentalForeignApi
import platform.UIKit.UIScreen

@OptIn(ExperimentalForeignApi::class)
actual fun getScreenWidth(): Int {
    return UIScreen.mainScreen.bounds.size
}