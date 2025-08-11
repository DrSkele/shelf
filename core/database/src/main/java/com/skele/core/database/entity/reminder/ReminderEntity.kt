package com.skele.core.database.entity.reminder

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant
import java.time.LocalDate

@Entity(tableName = "reminder")
data class ReminderEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val title: String,
    val endDate: LocalDate,
    val anchoredDate: LocalDate,
    val creationDate: Instant,
    val recurrenceType: RecurrenceType,
    val recurrenceInterval: Int,
    val recurrenceValue: Int,
)
