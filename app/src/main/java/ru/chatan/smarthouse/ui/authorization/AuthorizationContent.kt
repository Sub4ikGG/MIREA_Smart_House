package ru.chatan.smarthouse.ui.authorization

import android.graphics.Bitmap
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import ru.chatan.smarthouse.ui.UiConstants.defaultPadding
import ru.chatan.smarthouse.ui.UiConstants.smartContentSpace
import ru.chatan.smarthouse.ui.textfield.SmartHouseTextField
import ru.chatan.smarthouse.ui.theme.LocalSmartHouseTypography
import ru.chatan.smarthouse.ui.theme.SmartHouseTheme

@Composable
fun AuthorizationContent(
    isAuthorizationInProgress: Boolean,
    showIcon: Boolean = true,
    onAuthorize: (user: String, password: String) -> Unit
) {
    val userTextFieldValue = remember { mutableStateOf(TextFieldValue()) }
    val passwordTextFieldValue = remember { mutableStateOf(TextFieldValue()) }
    val keyboardController = LocalSoftwareKeyboardController.current

    fun callAuthorize() {
        keyboardController?.hide()

        onAuthorize(
            userTextFieldValue.value.text,
            passwordTextFieldValue.value.text
        )
    }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(defaultPadding),
            verticalArrangement = Arrangement.spacedBy(smartContentSpace),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(1f))

            if (showIcon) {
                val packageName = LocalContext.current.packageName
                val iconDrawable =
                    LocalContext.current.packageManager.getApplicationIcon(packageName)

                Image(
                    iconDrawable.toBitmap(config = Bitmap.Config.ARGB_8888).asImageBitmap(),
                    contentDescription = "Image",
                    modifier = Modifier.size(128.dp)
                )

                Spacer(modifier = Modifier.weight(1f))
            }

            SmartHouseTextField(
                hint = "Пользователь",
                value = userTextFieldValue.value,
                onValueChange = { userTextFieldValue.value = it },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )

            SmartHouseTextField(
                hint = "Пароль",
                value = passwordTextFieldValue.value,
                onValueChange = { passwordTextFieldValue.value = it },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { callAuthorize() })
            )

            Button(
                modifier = Modifier.imePadding(),
                onClick = { callAuthorize() },
            ) {
                AnimatedVisibility(visible = isAuthorizationInProgress) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(32.dp),
                        color = Color.White
                    )
                }

                AnimatedVisibility(visible = !isAuthorizationInProgress) {
                    Text(
                        text = "Войти в систему",
                        style = LocalSmartHouseTypography.current.titleSmall
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AuthorizationContentPreview() {
    SmartHouseTheme {
        AuthorizationContent(
            isAuthorizationInProgress = false,
            showIcon = false,
            onAuthorize = { _, _ -> }
        )
    }
}