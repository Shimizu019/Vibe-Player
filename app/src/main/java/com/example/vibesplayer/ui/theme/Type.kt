package com.example.vibesplayer.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.vibesplayer.R

val Fraunces = FontFamily(
    Font(R.font.fraunces_regular, FontWeight.Normal),
    Font(R.font.fraunces_semibold, FontWeight.SemiBold),
)

val InstrumentSans = FontFamily(
    Font(R.font.instrument_sans_regular, FontWeight.Normal),
    Font(R.font.instrument_sans_medium, FontWeight.Medium),
)

/** Type scale from spec section 7.3 (size/line height in sp). */
object VibesType {
    val titleXL = TextStyle(
        fontFamily = Fraunces, fontWeight = FontWeight.SemiBold,
        fontSize = 32.sp, lineHeight = 38.sp,
    )
    val titleL = TextStyle(
        fontFamily = Fraunces, fontWeight = FontWeight.SemiBold,
        fontSize = 30.sp, lineHeight = 36.sp,
    )
    val titleM = TextStyle(
        fontFamily = Fraunces, fontWeight = FontWeight.SemiBold,
        fontSize = 28.sp, lineHeight = 34.sp,
    )
    val heading = TextStyle(
        fontFamily = Fraunces, fontWeight = FontWeight.Normal,
        fontSize = 22.sp, lineHeight = 28.sp,
    )
    val artistL = TextStyle(
        fontFamily = InstrumentSans, fontWeight = FontWeight.Normal,
        fontSize = 20.sp, lineHeight = 26.sp,
    )
    val rowTitle = TextStyle(
        fontFamily = InstrumentSans, fontWeight = FontWeight.Medium,
        fontSize = 18.sp, lineHeight = 24.sp,
    )
    val input = TextStyle(
        fontFamily = InstrumentSans, fontWeight = FontWeight.Normal,
        fontSize = 18.sp, lineHeight = 24.sp,
    )
    val body = TextStyle(
        fontFamily = InstrumentSans, fontWeight = FontWeight.Normal,
        fontSize = 16.sp, lineHeight = 22.sp,
    )
    val bodyMed = TextStyle(
        fontFamily = InstrumentSans, fontWeight = FontWeight.Medium,
        fontSize = 16.sp, lineHeight = 22.sp,
    )
    val meta = TextStyle(
        fontFamily = InstrumentSans, fontWeight = FontWeight.Normal,
        fontSize = 14.sp, lineHeight = 20.sp,
    )
    val label = TextStyle(
        fontFamily = InstrumentSans, fontWeight = FontWeight.Medium,
        fontSize = 14.sp, lineHeight = 20.sp, letterSpacing = 1.2.sp,
    )
    val labelL = TextStyle(
        fontFamily = InstrumentSans, fontWeight = FontWeight.Medium,
        fontSize = 16.sp, lineHeight = 22.sp, letterSpacing = 1.5.sp,
    )
}

val VibesTypography = Typography(
    displayLarge = VibesType.titleXL,
    headlineLarge = VibesType.titleL,
    headlineMedium = VibesType.titleM,
    headlineSmall = VibesType.heading,
    titleLarge = VibesType.artistL,
    titleMedium = VibesType.rowTitle,
    bodyLarge = VibesType.input,
    bodyMedium = VibesType.body,
    bodySmall = VibesType.meta,
    labelLarge = VibesType.labelL,
    labelMedium = VibesType.label,
)
