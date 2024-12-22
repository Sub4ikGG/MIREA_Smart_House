package ru.chatan.smarthouse.service.logs

import io.ktor.util.date.toDate
import kotlinx.serialization.Serializable
import java.time.ZoneId
import java.util.Calendar
import java.util.TimeZone

@Serializable
class ServiceLog(
    val user: String,
    val log: String,
    val timestamp: Long = System.currentTimeMillis()
) {
    fun prettyString(): String {
        val calendar = Calendar.getInstance(TimeZone.getTimeZone(ZoneId.systemDefault()))
        val gmtDate = calendar.toDate(timestamp)

        val fixedHours = if (gmtDate.hours.toString().length == 1) "0${gmtDate.hours}" else gmtDate.hours
        val fixedMinutes = if (gmtDate.minutes.toString().length == 1) "0${gmtDate.minutes}" else gmtDate.minutes
        val fixedSeconds = if (gmtDate.seconds.toString().length == 1) "0${gmtDate.seconds}" else gmtDate.seconds

        val date = "${gmtDate.dayOfMonth} ${gmtDate.month.value} ${gmtDate.year} ${fixedHours}:${fixedMinutes}:${fixedSeconds}"

        return "Пользователь: ${this.user}\nДействие: ${this.log}\nДата: $date"
    }
}