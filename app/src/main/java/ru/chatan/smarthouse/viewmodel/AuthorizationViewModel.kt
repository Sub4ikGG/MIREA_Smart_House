package ru.chatan.smarthouse.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.chatan.smarthouse.service.authorization.AuthorizationService

class AuthorizationViewModel : ViewModel() {
    private val authorizationService = AuthorizationService.resolve()
    val state = authorizationService.state

    fun checkAuthorization(context: Context) {
        viewModelScope.launch { authorizationService.checkAuthorization(context = context) }
    }

    fun authorize(context: Context, user: String, password: String) {
        viewModelScope.launch {
            authorizationService.authorize(
                context = context,
                user = user,
                password = password
            )
        }
    }
}