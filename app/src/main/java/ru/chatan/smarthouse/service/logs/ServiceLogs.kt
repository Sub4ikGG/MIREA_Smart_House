package ru.chatan.smarthouse.service.logs

import kotlinx.serialization.Serializable

@Serializable
class ServiceLogs(
    val data: List<ServiceLog> = emptyList()
)