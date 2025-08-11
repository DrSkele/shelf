package com.skele.core.database.entity.reminder

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(
    tableName = "remind_date",
    foreignKeys = [
        ForeignKey(
            entity = ReminderEntity::class,
            parentColumns = ["id"],
            childColumns = ["taskId"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
)
data class RemindDateEntity(
    @PrimaryKey val id: Long,
    val reminderId: Long,
    val completed: Boolean,
    val dueDate: LocalDate,
)
