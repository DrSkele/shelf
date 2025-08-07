package com.skele.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "recurrence_days",
    foreignKeys = [
        ForeignKey(
            entity = ReminderEntity::class,
            parentColumns = ["id"],
            childColumns = ["taskId"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
)
data class RecurrenceDayOfWeekEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(index = true)
    val taskId: Int,
    val dayOfWeek: Int,
)
