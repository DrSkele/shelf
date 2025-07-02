package com.skele.core.database.converter

import androidx.room.TypeConverter
import com.skele.core.database.entity.todo.ToDoData
import kotlinx.serialization.json.Json

class ToDoDataConverter {
    @TypeConverter
    fun fromToDoData(data: ToDoData): String = Json.encodeToString(ToDoData.serializer(), data)

    @TypeConverter
    fun toToDoData(data: String): ToDoData = Json.decodeFromString(ToDoData.serializer(), data)
}
