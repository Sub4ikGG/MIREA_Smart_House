package ru.chatan.smarthouse

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.chatan.smarthouse.service.authorization.AuthorizationState
import ru.chatan.smarthouse.ui.SmartContent
import ru.chatan.smarthouse.ui.authorization.AuthorizationContent
import ru.chatan.smarthouse.ui.theme.SmartHouseTheme
import ru.chatan.smarthouse.viewmodel.AuthorizationViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            val context = LocalContext.current.applicationContext
            val authorizationViewModel: AuthorizationViewModel = viewModel()
            val authorizationState by authorizationViewModel.state.collectAsStateWithLifecycle(
                initialValue = AuthorizationState.None
            )

            LaunchedEffect(key1 = Unit) {
                authorizationViewModel.checkAuthorization(context = context)
            }

            LaunchedEffect(key1 = authorizationState) {
                (authorizationState as? AuthorizationState.NotAuthorized)?.let {
                    if (it.message != null) {
                        Toast.makeText(
                            context,
                            "Неверные пользовательские данные",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

            SmartHouseTheme {
                if (authorizationState is AuthorizationState.Authorized)
                    SmartContent()

                AnimatedVisibility(
                    visible = authorizationState !is AuthorizationState.Authorized,
                    enter = scaleIn(),
                    exit = fadeOut()
                ) {
                    AuthorizationContent(
                        isAuthorizationInProgress = authorizationState is AuthorizationState.Loading,
                        onAuthorize = { user, password ->
                            authorizationViewModel.authorize(
                                context = context,
                                user = user,
                                password = password
                            )
                        }
                    )
                }
            }
        }
    }
}