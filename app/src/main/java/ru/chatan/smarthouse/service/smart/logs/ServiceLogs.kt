package ru.chatan.smarthouse.service.smart.logs

import kotlinx.serialization.Serializable

@Serializable
class ServiceLogs(
    val data: List<ServiceLog> = emptyList()
)