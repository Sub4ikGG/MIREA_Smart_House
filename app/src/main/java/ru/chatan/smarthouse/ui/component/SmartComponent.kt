package ru.chatan.smarthouse.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.chatan.smarthouse.R
import ru.chatan.smarthouse.service.smart.LampState
import ru.chatan.smarthouse.ui.UiConstants.defaultPadding
import ru.chatan.smarthouse.ui.component.controls.ComponentControls
import ru.chatan.smarthouse.ui.theme.Card
import ru.chatan.smarthouse.ui.theme.SmartHouseTheme

@Composable
fun SmartComponent(
    lampState: LampState,
    onToggleLamp: () -> Unit,
    onShowLogs: () -> Unit = {}
) {
    val showSettings = remember { mutableStateOf(false) }
    val componentShape = RoundedCornerShape(24.dp)

    Box(
        contentAlignment = Alignment.CenterEnd
    ) {
        Image(
            modifier = Modifier
                .height(140.dp)
                .border(width = 8.dp, color = Card, shape = componentShape)
                .clip(componentShape)
                .clickable { showSettings.value = !showSettings.value },
            painter = painterResource(id = R.drawable.lamp_image),
            contentDescription = "Lamp",
            contentScale = ContentScale.Crop
        )

        AnimatedVisibility(
            visible = showSettings.value,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            ComponentControls(
                onToggleLamp = onToggleLamp,
                onShowLogs = onShowLogs,
                lampState = lampState,
                modifier = Modifier.padding(end = 19.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SmartComponentPreview() {
    SmartHouseTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(defaultPadding)
        ) {
            SmartComponent(
                lampState = LampState.Disabled,
                onToggleLamp = {}
            )
        }
    }
}