package org.opengripboard.theming

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import opengripboard.shared.generated.resources.InstrumentSans_Bold
import opengripboard.shared.generated.resources.InstrumentSans_Regular
import opengripboard.shared.generated.resources.Res
import org.jetbrains.compose.resources.Font

@Composable
fun appTypography(): Typography {
    val instrumentSans = FontFamily(
        Font(Res.font.InstrumentSans_Bold, FontWeight.Bold),
        Font(Res.font.InstrumentSans_Regular, FontWeight.Normal),
    )


    return androidx.compose.material3.Typography(
        headlineLarge = TextStyle(
            fontFamily = instrumentSans,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        ),
        bodyMedium = TextStyle(
            fontFamily = instrumentSans,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp
        ),
        displaySmall = TextStyle(
            fontFamily = instrumentSans,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp
        ),
        displayLarge = TextStyle(
            fontFamily = instrumentSans,
            fontWeight = FontWeight.Medium,
            fontSize = 24.sp
    )
    )
}