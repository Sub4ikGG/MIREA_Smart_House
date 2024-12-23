package ru.chatan.smarthouse.service.smart

sealed class LampState {
    data object Enabled: LampState()
    data object Disabled: LampState()
    data object Loading : LampState()
}
