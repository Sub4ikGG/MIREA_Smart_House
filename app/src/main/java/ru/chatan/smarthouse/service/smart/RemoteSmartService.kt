package ru.chatan.smarthouse.service.smart

import android.util.Log
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.put
import io.ktor.http.isSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import ru.chatan.smarthouse.http.HttpConstants
import ru.chatan.smarthouse.http.SmartHouseHttpClient
import ru.chatan.smarthouse.service.smart.lamp.GetLampState
import ru.chatan.smarthouse.service.smart.lamp.ToggleLampState
import ru.chatan.smarthouse.service.smart.logs.ServiceLogs

class RemoteSmartService : SmartService {
    private val serviceMutex = Mutex()
    private val mutableState: MutableStateFlow<LampState> = MutableStateFlow(LampState.Loading)

    override val state: Flow<LampState>
        get() = mutableState.asStateFlow()

    override suspend fun getLampState(stateLoading: Boolean) {
        serviceMutex.withLock {
            if (stateLoading)
                mutableState.update { LampState.Loading }

            try {
                withContext(Dispatchers.IO) {
                    val httpResponse =
                        SmartHouseHttpClient.resolveHttpClient().get(HttpConstants.GET_LAMP_URL)

                    if (httpResponse.status.isSuccess()) {
                        val getLampState = httpResponse.body<GetLampState>()
                        val lampState =
                            if (getLampState.data) LampState.Enabled else LampState.Disabled
                        mutableState.update { lampState }

                        return@withContext
                    }

                    Log.e(TAG, "getLampState: $httpResponse")

                    mutableState.update { LampState.Disabled }
                }
            } catch (e: Exception) {
                Log.e(TAG, e.localizedMessage.orEmpty())
                mutableState.update { LampState.Disabled }
            }
        }
    }

    override suspend fun toggleLamp() {
        serviceMutex.withLock {
            mutableState.update { LampState.Loading }

            try {
                withContext(Dispatchers.IO) {
                    val httpResponse = SmartHouseHttpClient.resolveHttpClient()
                        .put(HttpConstants.PUT_TOGGLE_LAMP_URL)

                    if (httpResponse.status.isSuccess()) {
                        val toggleLampState = httpResponse.body<ToggleLampState>()
                        val lampState =
                            if (toggleLampState.data) LampState.Enabled else LampState.Disabled
                        mutableState.update { lampState }

                        return@withContext
                    }

                    Log.e(TAG, "toggleLamp: $httpResponse")

                    mutableState.update { LampState.Disabled }
                }
            } catch (e: Exception) {
                Log.e(TAG, e.localizedMessage.orEmpty())

                mutableState.update { LampState.Disabled }
            }
        }
    }

    override suspend fun getLogs(): ServiceLogs {
        try {
            val httpResponse =
                SmartHouseHttpClient.resolveHttpClient().get(HttpConstants.GET_LOGS_URL)

            return if (httpResponse.status.isSuccess()) {
                httpResponse.body<ServiceLogs>()
            } else {
                Log.e(TAG, "getLogs: $httpResponse")
                ServiceLogs()
            }
        } catch (e: Exception) {
            Log.e(TAG, e.localizedMessage.orEmpty())
            return ServiceLogs()
        }
    }

    companion object {
        private const val TAG = "RemoteSmartService"
    }
}