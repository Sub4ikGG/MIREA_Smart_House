package ru.chatan.smarthouse.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import ru.chatan.smarthouse.service.smart.SmartService
import ru.chatan.smarthouse.ui.dialog.logs.ServiceLogsDialogState

class SmartViewModel : ViewModel() {
    private val smartService = SmartService.resolve()
    val lampState = smartService.state

    private val mutableDialogState = MutableStateFlow(ServiceLogsDialogState())
    val dialogState = mutableDialogState.asStateFlow()

    init {
        viewModelScope.launch {
            while (isActive) {
                getLampState()
                delay(500L)
            }
        }
    }

    private fun getLampState() {
        viewModelScope.launch { smartService.getLampState(stateLoading = false) }
    }

    fun toggleLamp() {
        viewModelScope.launch { smartService.toggleLamp() }
    }

    fun showLogs() {
        mutableDialogState.update {
            ServiceLogsDialogState(showDialog = true, isLoading = true)
        }

        viewModelScope.launch {
            val logs = smartService.getLogs()

            mutableDialogState.update {
                ServiceLogsDialogState(showDialog = true, logs = logs)
            }
        }
    }

    fun hideDialog() {
        mutableDialogState.update { ServiceLogsDialogState(showDialog = false) }
    }
}