package ru.chatan.smarthouse.service.authorization

import android.content.Context
import android.util.Log
import io.ktor.client.request.get
import io.ktor.http.isSuccess
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import ru.chatan.smarthouse.http.HttpConstants
import ru.chatan.smarthouse.http.SmartHouseHttpClient
import ru.chatan.smarthouse.service.CommonServiceConstants.AUTHORIZATION_PREFS
import ru.chatan.smarthouse.service.CommonServiceConstants.PASSWORD_PREFS
import ru.chatan.smarthouse.service.CommonServiceConstants.USER_PREFS

class RemoteAuthorizationService : AuthorizationService {
    private val mutableState: MutableStateFlow<AuthorizationState> =
        MutableStateFlow(AuthorizationState.None)

    override val state: Flow<AuthorizationState> = mutableState.asStateFlow()

    override suspend fun checkAuthorization(context: Context) {
        val prefs = context.getPrefs() ?: return

        val user = prefs.getString(USER_PREFS, null) ?: return
        val password = prefs.getString(PASSWORD_PREFS, null) ?: return

        authorize(
            automatic = true,
            context = context,
            user = user,
            password = password
        )
    }

    override suspend fun authorize(
        context: Context,
        automatic: Boolean,
        user: String,
        password: String
    ) {
        try {
            mutableState.update { AuthorizationState.Loading }

            delay(1_500L)

            SmartHouseHttpClient.rebuildHttpClient(user = user, password = password)

            val httpClient = SmartHouseHttpClient.resolveHttpClient()
            val httpResponse = httpClient.get(HttpConstants.GET_AUTHORIZE_URL)

            if (httpResponse.status.isSuccess()) {
                saveAuthorization(context = context, user = user, password = password)
                mutableState.update { AuthorizationState.Authorized }
                return
            }

            Log.e(TAG, "authorize: $httpResponse")

            if (automatic)
                mutableState.update { AuthorizationState.NotAuthorized() }
            else mutableState.update { AuthorizationState.NotAuthorized("Неверные пользовательские данные") }
        } catch (_: Exception) { mutableState.update { AuthorizationState.NotAuthorized() } }
    }

    private fun saveAuthorization(
        context: Context,
        user: String,
        password: String
    ) {
        val prefs = context.getPrefs() ?: return
        prefs.edit().also {
            it.putString(USER_PREFS, user)
            it.putString(PASSWORD_PREFS, password)
            it.apply()
        }
    }

    private fun Context.getPrefs() = this.getSharedPreferences(AUTHORIZATION_PREFS, Context.MODE_PRIVATE) ?: null

    private companion object {
        const val TAG = "RemoteAuthorizationService"
    }
}