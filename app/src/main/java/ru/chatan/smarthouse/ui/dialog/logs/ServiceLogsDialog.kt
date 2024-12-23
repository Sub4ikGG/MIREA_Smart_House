package ru.chatan.smarthouse.ui.dialog.logs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.chatan.smarthouse.service.smart.logs.ServiceLog
import ru.chatan.smarthouse.service.smart.logs.ServiceLogs
import ru.chatan.smarthouse.ui.theme.LocalSmartHouseTypography
import ru.chatan.smarthouse.ui.theme.SmartHouseTheme

@Composable
fun ServiceLogsDialog(
    isLoading: Boolean = false,
    serviceLogs: ServiceLogs = ServiceLogs(),
    onDismissRequest: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            Button(onClick = onDismissRequest) {
                Text(text = "OK", style = LocalSmartHouseTypography.current.titleSmall)
            }
        },
        icon = { Icons.Default.Info },
        title = { Text(text = "Умная лампочка", style = LocalSmartHouseTypography.current.titleMedium) },
        text = {
            if (isLoading) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }

                return@AlertDialog
            }

            if (serviceLogs.data.isEmpty()) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Пока что логов нет :(",
                    style = LocalSmartHouseTypography.current.bodySmall
                )
            } else {
                LazyColumn {
                    items(serviceLogs.data) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = it.prettyString(),
                            style = LocalSmartHouseTypography.current.bodySmall
                        )

                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun ServiceLogsDialogPreview() {
    val logs = ServiceLogs(
        data = List(10) { _ -> ServiceLog(user = "admin", log = "Включил лампочку") }
    )

    SmartHouseTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            ServiceLogsDialog(serviceLogs = logs) {

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ServiceLogsDialogNoLogsPreview() {
    val logs = ServiceLogs()

    SmartHouseTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            ServiceLogsDialog(serviceLogs = logs) {

            }
        }
    }
}