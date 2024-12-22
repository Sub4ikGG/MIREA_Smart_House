package ru.chatan.smarthouse.service.lamp

import kotlinx.serialization.Serializable

@Serializable
class GetLampState(
    val data: Boolean
)