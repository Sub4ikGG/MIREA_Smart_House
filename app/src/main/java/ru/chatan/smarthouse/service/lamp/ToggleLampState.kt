package ru.chatan.smarthouse.service.lamp

import kotlinx.serialization.Serializable

@Serializable
class ToggleLampState(
    val data: Boolean
)