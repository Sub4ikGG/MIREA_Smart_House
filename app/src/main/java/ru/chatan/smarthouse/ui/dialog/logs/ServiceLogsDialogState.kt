package ru.chatan.smarthouse.ui.dialog.logs

import androidx.compose.runtime.Immutable
import ru.chatan.smarthouse.service.smart.logs.ServiceLogs

@Immutable
data class ServiceLogsDialogState(
    val isLoading: Boolean = false,
    val showDialog: Boolean = false,
    val logs: ServiceLogs = ServiceLogs()
)
