package ru.chatan.smarthouse.service

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext

class RemoteSmartService : SmartService {
    private val mutableState: MutableStateFlow<LampState> = MutableStateFlow(LampState.Loading)

    override val state: Flow<LampState>
        get() = mutableState.asStateFlow()

    override suspend fun getLampState() {
        mutableState.update { LampState.Loading }

        withContext(Dispatchers.IO) {
            // TODO("Resolve Lamp State")

            mutableState.update { TODO("Resolve Lamp State") }
        }
    }

    override suspend fun toggleLamp() {
        mutableState.update { LampState.Loading }

        withContext(Dispatchers.IO) {
            // TODO("Toggle Lamp")

            mutableState.update { TODO("Resolve Lamp State") }
        }
    }
}