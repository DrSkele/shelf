package com.skele.core.database.converter

import androidx.room.TypeConverter
import com.skele.core.database.util.DayOfWeekSerializer
import com.skele.core.model.common.Schedule
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import java.time.DayOfWeek

class ScheduleConverter {
    private val json =
        Json {
            serializersModule =
                SerializersModule {
                    contextual(DayOfWeek::class, DayOfWeekSerializer)
                }
        }

    @TypeConverter
    fun fromSchedule(schedule: Schedule): String = json.encodeToString(schedule)

    @TypeConverter
    fun toSchedule(jsonString: String): Schedule = json.decodeFromString(jsonString)
}
