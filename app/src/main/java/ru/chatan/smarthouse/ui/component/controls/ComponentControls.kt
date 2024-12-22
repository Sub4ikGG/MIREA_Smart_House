package ru.chatan.smarthouse.ui.component.controls

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.chatan.smarthouse.R
import ru.chatan.smarthouse.service.LampState
import ru.chatan.smarthouse.ui.theme.Ocean
import ru.chatan.smarthouse.ui.theme.SmartHouseTheme
import ru.chatan.smarthouse.ui.theme.noTintClickable

@Composable
fun ComponentControls(
    modifier: Modifier = Modifier,
    lampState: LampState,
    onToggleLamp: () -> Unit,
    onShowLogs: () -> Unit = {},
) {
    val settingsShape = RoundedCornerShape(16.dp)
    Box(
        modifier = modifier
            .clip(settingsShape)
            .width(50.dp)
            .height(105.dp)
            .background(Color.Black.copy(alpha = 0.6f))
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)) {
            Icon(
                modifier = Modifier.weight(1f).noTintClickable(onClick = onShowLogs),
                painter = painterResource(id = R.drawable.info_icon),
                contentDescription = "Info Button",
                tint = Color.White
            )

            when (lampState) {
                LampState.Disabled ->
                    Icon(
                        modifier = Modifier.weight(1f).noTintClickable(onClick = onToggleLamp),
                        painter = painterResource(id = R.drawable.on_icon),
                        contentDescription = "Toggle Button",
                        tint = Color.White
                    )

                LampState.Enabled ->
                    Icon(
                        modifier = Modifier.weight(1f).noTintClickable(onClick = onToggleLamp),
                        painter = painterResource(id = R.drawable.on_icon),
                        contentDescription = "Toggle Button",
                        tint = Ocean
                    )

                LampState.Loading ->
                    Box(
                        modifier = Modifier.weight(1f).padding(top = 4.dp, bottom = 2.dp).padding(horizontal = 2.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            color = Color.White,
                        )
                    }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ComponentControlsPreview() {
    SmartHouseTheme {
        ComponentControls(
            onToggleLamp = {},
            lampState = LampState.Disabled
        )
    }
}