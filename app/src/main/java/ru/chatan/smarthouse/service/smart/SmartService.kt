package ru.chatan.smarthouse.service.smart

import kotlinx.coroutines.flow.Flow
import ru.chatan.smarthouse.service.smart.logs.ServiceLogs

interface SmartService {
    val state: Flow<LampState>

    suspend fun getLampState(stateLoading: Boolean = true)
    suspend fun toggleLamp()
    suspend fun getLogs(): ServiceLogs

    companion object {
        fun resolve(): SmartService = RemoteSmartService()
    }
}