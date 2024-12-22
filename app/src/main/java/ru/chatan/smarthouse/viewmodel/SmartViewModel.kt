package ru.chatan.smarthouse.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.chatan.smarthouse.service.SmartService

class SmartViewModel : ViewModel() {
    private val smartService = SmartService.resolve()
    val lampState = smartService.state

    init {
        getLampState()
    }

    private fun getLampState() {
        viewModelScope.launch { smartService.getLampState() }
    }

    fun toggleLamp() {
        viewModelScope.launch { smartService.toggleLamp() }
    }
}