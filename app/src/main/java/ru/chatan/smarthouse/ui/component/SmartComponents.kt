package ru.chatan.smarthouse.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.chatan.smarthouse.service.smart.LampState
import ru.chatan.smarthouse.ui.UiConstants.defaultPadding
import ru.chatan.smarthouse.ui.UiConstants.defaultSpace
import ru.chatan.smarthouse.ui.theme.LocalSmartHouseTypography
import ru.chatan.smarthouse.ui.theme.SmartHouseTheme

@Composable
fun SmartComponents(
    onToggleLamp: () -> Unit,
    onShowLogs: () -> Unit,
    lampState: LampState
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(defaultSpace)
    ) {
        Text(
            modifier = Modifier.padding(start = 4.dp),
            text = "Умные лампочки",
            style = LocalSmartHouseTypography.current.titleMedium
        )

        SmartComponent(
            onToggleLamp = onToggleLamp,
            onShowLogs = onShowLogs,
            lampState = lampState
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SmartComponentsPreview() {
    SmartHouseTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(defaultPadding),
        ) {
            SmartComponents(
                onToggleLamp = {},
                onShowLogs = {},
                lampState = LampState.Disabled
            )
        }
    }
}