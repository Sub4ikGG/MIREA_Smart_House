package ru.chatan.smarthouse.service.authorization

import android.content.Context
import kotlinx.coroutines.flow.Flow

interface AuthorizationService {
    val state: Flow<AuthorizationState>

    suspend fun checkAuthorization(context: Context)
    suspend fun authorize(
        context: Context,
        automatic: Boolean = false,
        user: String,
        password: String
    )

    companion object {
        fun resolve(): AuthorizationService = RemoteAuthorizationService()
    }
}