package ru.chatan.smarthouse.ui.header

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.chatan.smarthouse.ui.UiConstants.defaultCornerRadius
import ru.chatan.smarthouse.ui.UiConstants.defaultPadding
import ru.chatan.smarthouse.ui.theme.Brick
import ru.chatan.smarthouse.ui.theme.DarkBrick
import ru.chatan.smarthouse.ui.theme.LocalSmartHouseTypography
import ru.chatan.smarthouse.ui.theme.SmartHouseTheme

@Composable
fun SmartHeader() {
    val headerWidth = 72.dp
    val gradient = Brush.horizontalGradient(
        colorStops = arrayOf(
            0.23f to Brick,
            1f to DarkBrick
        )
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(headerWidth)
            .clip(RoundedCornerShape(defaultCornerRadius))
            .background(gradient),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Dashboard - Умный дом",
            style = LocalSmartHouseTypography.current.titleSmall,
            color = Color.White
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SmartHeaderPreview() {
    SmartHouseTheme {
        Column(modifier = Modifier.fillMaxSize().padding(defaultPadding)) {
            SmartHeader()
        }
    }
}