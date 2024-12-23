package ru.chatan.smarthouse.service.authorization

sealed class AuthorizationState {
    data object Loading : AuthorizationState()
    data object Authorized : AuthorizationState()
    data class NotAuthorized(val message: String? = null) : AuthorizationState()

    data object None : AuthorizationState()
}