package com.skele.core.database.entity.todo

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.skele.core.database.converter.ScheduleConverter
import com.skele.core.database.converter.ToDoDataConverter
import com.skele.core.model.common.Schedule

@Entity(tableName = ToDoEntity.tableName)
@TypeConverters(ToDoDataConverter::class, ScheduleConverter::class)
data class ToDoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val description: String,
    val isDone: Boolean,
    val schedule: Schedule,
    val timeStamp: Long,
    val data: ToDoData,
) {
    companion object {
        const val tableName = "todo"
    }
}
