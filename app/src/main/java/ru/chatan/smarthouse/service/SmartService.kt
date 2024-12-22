package ru.chatan.smarthouse.service

import kotlinx.coroutines.flow.Flow

interface SmartService {
    val state: Flow<LampState>

    suspend fun getLampState()
    suspend fun toggleLamp()

    companion object {
        fun resolve(): SmartService = RemoteSmartService()
    }
}