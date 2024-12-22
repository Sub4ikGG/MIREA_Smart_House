package ru.chatan.smarthouse.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.chatan.smarthouse.service.LampState
import ru.chatan.smarthouse.ui.UiConstants.defaultPadding
import ru.chatan.smarthouse.ui.UiConstants.smartContentSpace
import ru.chatan.smarthouse.ui.component.SmartComponents
import ru.chatan.smarthouse.ui.dialog.logs.ServiceLogsDialog
import ru.chatan.smarthouse.ui.header.SmartHeader
import ru.chatan.smarthouse.ui.theme.SmartHouseTheme
import ru.chatan.smarthouse.viewmodel.SmartViewModel

@Composable
fun SmartContent() {
    val viewModel: SmartViewModel = viewModel()

    val lampState by viewModel.lampState.collectAsStateWithLifecycle(initialValue = LampState.Loading)
    val dialogState by viewModel.dialogState.collectAsStateWithLifecycle()

    if (dialogState.showDialog) {
        ServiceLogsDialog(
            isLoading = dialogState.isLoading,
            serviceLogs = dialogState.logs,
            onDismissRequest = viewModel::hideDialog
        )
    }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(defaultPadding),
            verticalArrangement = Arrangement.spacedBy(smartContentSpace)
        ) {
            SmartHeader()
            SmartComponents(
                onToggleLamp = viewModel::toggleLamp,
                onShowLogs = viewModel::showLogs,
                lampState = lampState
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SmartContentPreview() {
    SmartHouseTheme {
        SmartContent()
    }
}