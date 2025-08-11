package com.skele.core.database.converter

import androidx.room.TypeConverter
import java.time.LocalDate

class DateTimeConverter {
    @TypeConverter
    fun fromTimestamp(value: Long?): LocalDate? {
        // Convert Long back to LocalDate
        return value?.let { LocalDate.ofEpochDay(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: LocalDate?): Long? {
        // Convert LocalDate to a Long
        return date?.toEpochDay()
    }
}