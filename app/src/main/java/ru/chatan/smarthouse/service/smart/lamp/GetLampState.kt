package ru.chatan.smarthouse.service.smart.lamp

import kotlinx.serialization.Serializable

@Serializable
class GetLampState(
    val data: Boolean
)