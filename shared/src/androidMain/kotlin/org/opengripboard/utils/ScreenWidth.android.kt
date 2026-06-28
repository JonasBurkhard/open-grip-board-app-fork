package org.opengripboard.utils

import android.content.res.Resources

actual fun getScreenWidth(): Int {
    return Resources.getSystem().displayMetrics.widthPixels
}