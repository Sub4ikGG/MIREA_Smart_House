package ru.chatan.smarthouse.http

object HttpConstants {
    private const val HOST = "http://150.241.79.49:8080/"

    const val GET_LAMP_URL = HOST + "lamp"
    const val PUT_TOGGLE_LAMP_URL = HOST + "lamp"
    const val GET_LOGS_URL = HOST + "logs"
    const val GET_AUTHORIZE_URL = HOST + "lamp"
}