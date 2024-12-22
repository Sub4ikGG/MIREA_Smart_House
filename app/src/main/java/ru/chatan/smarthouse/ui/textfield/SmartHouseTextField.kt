package ru.chatan.smarthouse.ui.textfield

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.chatan.smarthouse.ui.UiConstants.defaultCornerRadius
import ru.chatan.smarthouse.ui.theme.Brick
import ru.chatan.smarthouse.ui.theme.LocalSmartHouseTypography
import ru.chatan.smarthouse.ui.theme.SmartHouseTheme

@Composable
fun SmartHouseTextField(
    modifier: Modifier = Modifier,
    value: TextFieldValue,
    hint: String = "",
    onValueChange: (TextFieldValue) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
    singleLine: Boolean = true,
    focusRequester: FocusRequester = FocusRequester.Default
) {
    Column(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 50.dp)
                .border(
                    border = BorderStroke(
                        width = 1.dp,
                        color = Color.DarkGray
                    ),
                    shape = RoundedCornerShape(defaultCornerRadius)
                )
                .padding(PaddingValues(horizontal = 16.dp)),
            contentAlignment = Alignment.CenterStart
        ) {
            CompositionLocalProvider(
                LocalTextSelectionColors provides TextSelectionColors(
                    handleColor = Color.Gray,
                    backgroundColor = Color.Gray
                )
            ) {
                BasicTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester),
                    textStyle = LocalSmartHouseTypography.current.titleSmall.copy(color = Color.Black),
                    value = value,
                    onValueChange = { textFieldValue ->
                        if (keyboardOptions.keyboardType == KeyboardType.Decimal) {
                            onValueChange(textFieldValue.copy(textFieldValue.text.filter { it.isDigit() }))
                        } else {
                            onValueChange(textFieldValue.copy(textFieldValue.text))
                        }
                    },
                    keyboardOptions = keyboardOptions,
                    singleLine = singleLine,
                    decorationBox = {
                        if (value.text.isEmpty())
                            Text(
                                text = hint,
                                style = LocalSmartHouseTypography.current.titleSmall,
                                color = Color.Gray
                            )

                        it.invoke()
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OutlinedTextFieldEnabledPreview() {
    SmartHouseTheme {
        SmartHouseTextField(
            modifier = Modifier.padding(16.dp),
            value = TextFieldValue("+7 (904) 657-75-79"),
            onValueChange = {},
            hint = "Введите номер телефона"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun OutlinedTextFieldErrorPreview() {
    SmartHouseTextField(
        modifier = Modifier.padding(16.dp),
        value = TextFieldValue(""),
        hint = "Введите номер телефона",
        onValueChange = {},
    )
}