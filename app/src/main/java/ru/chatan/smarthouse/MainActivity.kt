package ru.chatan.smarthouse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.chatan.smarthouse.service.LampState
import ru.chatan.smarthouse.ui.SmartContent
import ru.chatan.smarthouse.ui.authorization.AuthorizationContent
import ru.chatan.smarthouse.ui.dialog.logs.ServiceLogsDialog
import ru.chatan.smarthouse.ui.theme.SmartHouseTheme
import ru.chatan.smarthouse.viewmodel.SmartViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            val isAuthorized = remember { mutableStateOf(false) }
            SmartHouseTheme {
                if (isAuthorized.value)
                    SmartContent()

                AnimatedVisibility(
                    visible = !isAuthorized.value,
                    enter = scaleIn(),
                    exit = fadeOut()
                ) {
                    AuthorizationContent(onAuthorize = { isAuthorized.value = true })
                }
            }
        }
    }
}