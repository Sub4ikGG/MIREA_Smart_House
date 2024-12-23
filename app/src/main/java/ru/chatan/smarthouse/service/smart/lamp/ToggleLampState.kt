package ru.chatan.smarthouse.service.smart.lamp

import kotlinx.serialization.Serializable

@Serializable
class ToggleLampState(
    val data: Boolean
)