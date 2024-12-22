package ru.chatan.smarthouse.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import ru.chatan.smarthouse.R

internal val LocalSmartHouseTypography = staticCompositionLocalOf { SmartHouseTypography() }

private val ubuntuFontFamily = FontFamily(
    Font(R.font.ubuntu_regular, FontWeight.Normal),
    Font(R.font.ubuntu_medium, FontWeight.Medium),
)

private val defaultTextStyle = TextStyle(fontFamily = ubuntuFontFamily, fontWeight = FontWeight.Normal)

@Immutable
class SmartHouseTypography(
    val titleSmall: TextStyle = defaultTextStyle.copy(
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        lineHeight = 20.sp
    ),
    val titleMedium: TextStyle = defaultTextStyle.copy(
        fontWeight = FontWeight.Medium,
        fontSize = 22.sp,
        lineHeight = 24.sp
    ),
    val bodySmall: TextStyle = defaultTextStyle.copy(
        fontSize = 14.sp,
        lineHeight = 16.sp
    ),
)