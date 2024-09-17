package com.example.todolisttutorial_exercicio

import androidx.room.TypeConverter
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class Converters {
    private val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")

    @TypeConverter
    fun fromLocalTime(localTime: LocalTime?): String? {
        return localTime?.format(formatter)
    }

    @TypeConverter
    fun toLocalTime(timeString: String?): LocalTime? {
        return timeString?.let { LocalTime.parse(it, formatter) }
    }
}